package com.aalperen.pharmacy_application.service;

import com.aalperen.pharmacy_application.entity.Medicine;
import com.aalperen.pharmacy_application.entity.OrderItem;
import com.aalperen.pharmacy_application.exception.BusinessException;
import com.aalperen.pharmacy_application.repository.MedicineRepository;
import com.aalperen.pharmacy_application.repository.OrderItemRepository;

import com.aalperen.pharmacy_application.response.generic.ReturnCodes;
import com.aalperen.pharmacy_application.util.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderItemServiceImp implements OrderItemService {

    private final MedicineRepository medicineRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    @Transactional(readOnly = true)
    public OrderItem getOrderItemById(Long id) {
        try {
            return orderItemRepository.findById(id)
                    .orElseThrow(() -> new BusinessException("Order item not found with id: " + id,
                            ReturnCodes.BAD_USER_CREDENTIALS.intValue(), "Order item not found with id: " + id));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ExceptionUtil.handleException(ex);
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItem> getItemsByOrderId(Long orderId) {
        try {
            return orderItemRepository.findByOrderId(orderId);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BusinessException(ReturnCodes.INTERNAL_SERVER_ERROR.description(),
                    ReturnCodes.INTERNAL_SERVER_ERROR.intValue(), ex.getMessage());
        }
    }

    @Override
    @Transactional
    public OrderItem updateOrderItemQuantity(Long itemId, int newQuantity) {
        try {
            OrderItem orderItem = getOrderItemById(itemId);
            Medicine medicine = orderItem.getMedicine();
            int quantityDifference = newQuantity - orderItem.getQuantity();
            if (medicine.getQuantity() < quantityDifference) {
                throw new BusinessException("Not enough medicine", ReturnCodes.NOT_ENOUGH_QUANTITY.intValue(), "Not " +
                        "enough medicine");
            }

            medicine.setQuantity(medicine.getQuantity() - quantityDifference);
            medicineRepository.save(medicine);
            orderItem.setQuantity(newQuantity);
            return orderItemRepository.save(orderItem);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ExceptionUtil.handleException(ex);
            return null;
        }
    }
}
