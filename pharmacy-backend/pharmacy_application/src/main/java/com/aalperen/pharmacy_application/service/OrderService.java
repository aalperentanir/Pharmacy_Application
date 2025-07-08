package com.aalperen.pharmacy_application.service;

import com.aalperen.pharmacy_application.entity.Order;
import com.aalperen.pharmacy_application.enums.OrderStatus;
import com.aalperen.pharmacy_application.request.OrderItemRequest;
import com.aalperen.pharmacy_application.request.OrderRequest;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    Order createOrder(OrderRequest req);

    Order getOrderById(Long id);

    List<Order> getAllOrders();

    List<Order> getOrdersByCustomerId(Long customerId);

    Order updateOrderStatus(Long orderId, OrderStatus status);

    void deleteOrder(Long id);

    Order addItemToOrder(Long orderId, OrderItemRequest itemRequest);

    Order removeItemFromOrder(Long orderId, Long itemId);

    BigDecimal calculateOrderTotal(Long orderId);
}
