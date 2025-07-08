package com.aalperen.pharmacy_application.service;

import com.aalperen.pharmacy_application.entity.Customer;
import com.aalperen.pharmacy_application.entity.Medicine;
import com.aalperen.pharmacy_application.entity.Order;
import com.aalperen.pharmacy_application.entity.OrderItem;
import com.aalperen.pharmacy_application.enums.OrderStatus;
import com.aalperen.pharmacy_application.exception.BusinessException;
import com.aalperen.pharmacy_application.repository.CustomerRepository;
import com.aalperen.pharmacy_application.repository.MedicineRepository;
import com.aalperen.pharmacy_application.repository.OrderItemRepository;
import com.aalperen.pharmacy_application.repository.OrderRepository;
import com.aalperen.pharmacy_application.request.OrderItemRequest;
import com.aalperen.pharmacy_application.request.OrderRequest;
import com.aalperen.pharmacy_application.response.generic.ReturnCodes;
import com.aalperen.pharmacy_application.util.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CustomerRepository customerRepository;
    private final MedicineRepository medicineRepository;


    @Override
    @Transactional
    public Order createOrder(OrderRequest orderRequest) {
        try {
            Customer customer =
                    customerRepository.findById(orderRequest.getCustomerId()).orElseThrow(() -> new BusinessException("Müşteri bulunamadı", ReturnCodes.CUSTOMER_NOT_FOUND.intValue(), "Müşteri bulunamadı"));

            Order order = new Order();
            order.setCustomer(customer);
            order.setStatus(OrderStatus.PENDING);
            order.setPurchaseDate(LocalDateTime.now());

            Order savedOrder = orderRepository.save(order);
            BigDecimal totalPrice = BigDecimal.ZERO;

            for (OrderItemRequest itemRequest : orderRequest.getItems()) {
                Medicine medicine =
                        medicineRepository.findById(itemRequest.getMedicineId()).orElseThrow(() -> new BusinessException("Ürün bulunamadı", ReturnCodes.PRODUCT_NOT_FOUND.intValue(), "Ürün Bulunamadı"));

                if (medicine.getQuantity() < itemRequest.getQuantity()) {
                    throw new BusinessException("Yetersiz stok: " + medicine.getBrand(),
                            ReturnCodes.INSUFFICIENT_STOCK.intValue(), "Yetersiz stok: " + medicine.getBrand());
                }

                medicine.setQuantity(medicine.getQuantity() - itemRequest.getQuantity());
                medicineRepository.save(medicine);

                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(savedOrder);
                orderItem.setMedicine(medicine);
                orderItem.setQuantity(itemRequest.getQuantity());
                orderItemRepository.save(orderItem);

                savedOrder.getOrderItems().add(orderItem);
                totalPrice = totalPrice.add(medicine.getPrice().multiply(new BigDecimal(itemRequest.getQuantity())));
            }

            savedOrder.setTotalPrice(totalPrice);
            return orderRepository.save(savedOrder);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ExceptionUtil.handleException(ex);
            return null;
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        try {
            return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ExceptionUtil.handleException(ex);
            return null;
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        try {
            return orderRepository.findAll();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BusinessException(ReturnCodes.INTERNAL_SERVER_ERROR.description(),
                    ReturnCodes.INTERNAL_SERVER_ERROR.intValue(), ex.getMessage());
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrdersByCustomerId(Long customerId) {
        try {
            return orderRepository.findOrdersByCustomerId(customerId);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BusinessException(ReturnCodes.INTERNAL_SERVER_ERROR.description(),
                    ReturnCodes.INTERNAL_SERVER_ERROR.intValue(), ex.getMessage());
        }

    }

    @Override
    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        try {
            Order order = getOrderById(orderId);
            order.setStatus(status);

            return orderRepository.save(order);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BusinessException(ReturnCodes.INTERNAL_SERVER_ERROR.description(),
                    ReturnCodes.INTERNAL_SERVER_ERROR.intValue(), ex.getMessage());
        }

    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        try {
            Order order = getOrderById(id);

            if (order.getStatus() != OrderStatus.CANCELLED) {
                order.getOrderItems().forEach(item -> {
                    Medicine medicine = item.getMedicine();
                    medicine.setQuantity(medicine.getQuantity() + item.getQuantity());
                    medicineRepository.save(medicine);
                });
            }
            orderItemRepository.deleteAll(order.getOrderItems());
            orderRepository.delete(order);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BusinessException(ReturnCodes.INTERNAL_SERVER_ERROR.description(),
                    ReturnCodes.INTERNAL_SERVER_ERROR.intValue(), ex.getMessage());
        }

    }

    @Override
    @Transactional
    public Order addItemToOrder(Long orderId, OrderItemRequest itemRequest) {
        try {
            Order order = getOrderById(orderId);
            Medicine medicine =
                    medicineRepository.findById(itemRequest.getMedicineId()).orElseThrow(() -> new BusinessException(
                            "Medicine not found with id: " + itemRequest.getMedicineId(),
                            ReturnCodes.MEDICINE_NOT_FOUND.intValue(), ReturnCodes.MEDICINE_NOT_FOUND.stringValue()));

            if (medicine.getQuantity() < itemRequest.getQuantity()) {
                throw new BusinessException("Not enough stock for medicine: " + medicine.getBrand(),
                        ReturnCodes.INSUFFICIENT_STOCK.intValue(), "Not enough stock for medicine: ");
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMedicine(medicine);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItemRepository.save(orderItem);

            BigDecimal newItemPrice = medicine.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            order.setTotalPrice(order.getTotalPrice().add(newItemPrice));

            medicine.setQuantity(medicine.getQuantity() - itemRequest.getQuantity());
            medicineRepository.save(medicine);

            return orderRepository.save(order);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ExceptionUtil.handleException(ex);
            return null;
        }
    }

    @Override
    @Transactional
    public Order removeItemFromOrder(Long orderId, Long itemId) {
        try {
            Order order = orderRepository.findById(orderId).orElseThrow(() -> new BusinessException("Order not found " +
                    "with id: " + orderId, ReturnCodes.INVALID_ORDER_ITEM.intValue(),
                    ReturnCodes.INVALID_ORDER_ITEM.stringValue()));

            OrderItem orderItem = orderItemRepository.findById(itemId).orElseThrow(() -> new BusinessException("Order" +
                    " item not found with id: " + itemId, ReturnCodes.INVALID_ORDER_ITEM.intValue(),
                    ReturnCodes.INVALID_ORDER_ITEM.stringValue()));

            if (!orderItem.getOrder().getId().equals(orderId)) {
                throw new BusinessException("Order item does not belong to order",
                        ReturnCodes.INVALID_ORDER_ITEM.intValue(), "Order item does not belong to order");
            }

            BigDecimal itemPrice =
                    orderItem.getMedicine().getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            order.setTotalPrice(order.getTotalPrice().subtract(itemPrice));

            Medicine medicine = orderItem.getMedicine();
            medicine.setQuantity(medicine.getQuantity() + orderItem.getQuantity());
            medicineRepository.save(medicine);

            order.getOrderItems().remove(orderItem);
            orderItemRepository.delete(orderItem);

            return orderRepository.save(order);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ExceptionUtil.handleException(ex);
            return null;
        }
    }


    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateOrderTotal(Long orderId) {
        Order order = getOrderById(orderId);
        return order.getOrderItems().stream().map(item -> item.getMedicine().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
