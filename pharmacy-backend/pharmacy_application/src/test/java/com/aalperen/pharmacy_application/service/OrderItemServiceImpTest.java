package com.aalperen.pharmacy_application.service;

import com.aalperen.pharmacy_application.entity.Medicine;
import com.aalperen.pharmacy_application.entity.OrderItem;
import com.aalperen.pharmacy_application.exception.BusinessException;
import com.aalperen.pharmacy_application.repository.MedicineRepository;
import com.aalperen.pharmacy_application.repository.OrderItemRepository;
import com.aalperen.pharmacy_application.response.generic.ReturnCodes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceImpTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private MedicineRepository medicineRepository;

    @InjectMocks
    private OrderItemServiceImp orderItemService;

    private OrderItem createTestOrderItem(Long id, int quantity, Medicine medicine) {
        OrderItem item = new OrderItem();
        item.setId(id);
        item.setQuantity(quantity);
        item.setMedicine(medicine);
        return item;
    }

    private Medicine createTestMedicine(Long id, int quantity) {
        Medicine medicine = new Medicine();
        medicine.setId(id);
        medicine.setQuantity(quantity);
        return medicine;
    }

    @Test
    void getOrderItemById_ShouldReturnOrderItem_WhenExists() {
        Long itemId = 1L;
        OrderItem expectedItem = createTestOrderItem(itemId, 2, createTestMedicine(1L, 10));
        when(orderItemRepository.findById(itemId)).thenReturn(Optional.of(expectedItem));

        OrderItem result = orderItemService.getOrderItemById(itemId);

        assertNotNull(result);
        assertEquals(itemId, result.getId());
        verify(orderItemRepository).findById(itemId);
    }

    @Test
    void getOrderItemById_ShouldThrowRuntimeException_WhenNotExists() {
        Long itemId = 1L;
        when(orderItemRepository.findById(itemId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> orderItemService.getOrderItemById(itemId));

        assertEquals("Order item not found with id: " + itemId, exception.getErrorMessage());
        verify(orderItemRepository).findById(itemId);
    }

    @Test
    void getOrderItemById_ShouldThrowBusinessException_WhenRepositoryFails() {
        Long itemId = 1L;
        when(orderItemRepository.findById(itemId)).thenThrow(new RuntimeException("DB Error"));

        BusinessException exception = assertThrows(BusinessException.class,
                () -> orderItemService.getOrderItemById(itemId));

        assertEquals("DB Error", exception.getErrorMessage());
    }

    @Test
    void getItemsByOrderId_ShouldReturnOrderItems_WhenExist() {
        Long orderId = 1L;
        List<OrderItem> expectedItems = List.of(
                createTestOrderItem(1L, 2, createTestMedicine(1L, 10)),
                createTestOrderItem(2L, 3, createTestMedicine(2L, 15))
        );
        when(orderItemRepository.findByOrderId(orderId)).thenReturn(expectedItems);

        List<OrderItem> result = orderItemService.getItemsByOrderId(orderId);

        assertEquals(2, result.size());
        verify(orderItemRepository).findByOrderId(orderId);
    }

    @Test
    void getItemsByOrderId_ShouldReturnEmptyList_WhenNoItemsExist() {
        Long orderId = 1L;
        when(orderItemRepository.findByOrderId(orderId)).thenReturn(Collections.emptyList());

        List<OrderItem> result = orderItemService.getItemsByOrderId(orderId);

        assertTrue(result.isEmpty());
        verify(orderItemRepository).findByOrderId(orderId);
    }

    @Test
    void getItemsByOrderId_ShouldThrowBusinessException_WhenRepositoryFails() {
        Long orderId = 1L;
        when(orderItemRepository.findByOrderId(orderId)).thenThrow(new RuntimeException("DB Error"));

        BusinessException exception = assertThrows(BusinessException.class,
                () -> orderItemService.getItemsByOrderId(orderId));

        assertEquals("DB Error", exception.getErrorMessage());
    }

    @Test
    void updateOrderItemQuantity_ShouldSuccess_WhenEnoughStock() {
        Long itemId = 1L;
        int initialQuantity = 2;
        int newQuantity = 4;
        int medicineStock = 10;
        
        Medicine medicine = createTestMedicine(1L, medicineStock);
        OrderItem orderItem = createTestOrderItem(itemId, initialQuantity, medicine);
        
        when(orderItemRepository.findById(itemId)).thenReturn(Optional.of(orderItem));
        when(medicineRepository.save(any(Medicine.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderItemRepository.save(any(OrderItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderItem result = orderItemService.updateOrderItemQuantity(itemId, newQuantity);

        assertEquals(newQuantity, result.getQuantity());
        assertEquals(medicineStock - (newQuantity - initialQuantity), result.getMedicine().getQuantity());
        verify(medicineRepository).save(medicine);
        verify(orderItemRepository).save(orderItem);
    }

    @Test
    void updateOrderItemQuantity_ShouldThrowBusinessException_WhenNotEnoughStock() {
        Long itemId = 1L;
        int initialQuantity = 2;
        int newQuantity = 5;
        int medicineStock = 2;
        
        Medicine medicine = createTestMedicine(1L, medicineStock);
        OrderItem orderItem = createTestOrderItem(itemId, initialQuantity, medicine);
        
        when(orderItemRepository.findById(itemId)).thenReturn(Optional.of(orderItem));

        BusinessException exception = assertThrows(BusinessException.class,
                () -> orderItemService.updateOrderItemQuantity(itemId, newQuantity));

        assertEquals("Not enough medicine", exception.getErrorMessage());
        assertEquals(medicineStock, medicine.getQuantity());
    }

    @Test
    void updateOrderItemQuantity_ShouldDecreaseStock_WhenQuantityReduced() {
        Long itemId = 1L;
        int initialQuantity = 5;
        int newQuantity = 2;
        int medicineStock = 10;
        
        Medicine medicine = createTestMedicine(1L, medicineStock);
        OrderItem orderItem = createTestOrderItem(itemId, initialQuantity, medicine);
        
        when(orderItemRepository.findById(itemId)).thenReturn(Optional.of(orderItem));
        when(medicineRepository.save(any(Medicine.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderItemRepository.save(any(OrderItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderItem result = orderItemService.updateOrderItemQuantity(itemId, newQuantity);

        assertEquals(newQuantity, result.getQuantity());
        assertEquals(medicineStock + (initialQuantity - newQuantity), result.getMedicine().getQuantity());
    }

    @Test
    void updateOrderItemQuantity_ShouldThrowBusinessException_WhenRepositoryFails() {
        Long itemId = 1L;
        int initialQuantity = 2;
        int newQuantity = 3;
        int medicineStock = 10;
        
        Medicine medicine = createTestMedicine(1L, medicineStock);
        OrderItem orderItem = createTestOrderItem(itemId, initialQuantity, medicine);
        
        when(orderItemRepository.findById(itemId)).thenReturn(Optional.of(orderItem));
        when(medicineRepository.save(any(Medicine.class))).thenThrow(new RuntimeException("DB Error"));

        BusinessException exception = assertThrows(BusinessException.class,
                () -> orderItemService.updateOrderItemQuantity(itemId, newQuantity));

        assertEquals("DB Error", exception.getErrorMessage());
    }
}