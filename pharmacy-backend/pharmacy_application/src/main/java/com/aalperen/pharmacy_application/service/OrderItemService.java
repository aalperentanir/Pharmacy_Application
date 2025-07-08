package com.aalperen.pharmacy_application.service;


import com.aalperen.pharmacy_application.entity.OrderItem;

import java.util.List;

public interface OrderItemService {

    OrderItem getOrderItemById(Long id);

    List<OrderItem> getItemsByOrderId(Long orderId);

    OrderItem updateOrderItemQuantity(Long itemId, int newQuantity);

}
