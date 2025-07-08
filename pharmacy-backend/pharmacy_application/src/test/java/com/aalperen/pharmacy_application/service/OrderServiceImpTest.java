package com.aalperen.pharmacy_application.service;

import com.aalperen.pharmacy_application.entity.*;
import com.aalperen.pharmacy_application.enums.OrderStatus;
import com.aalperen.pharmacy_application.exception.BusinessException;
import com.aalperen.pharmacy_application.repository.*;
import com.aalperen.pharmacy_application.request.OrderItemRequest;
import com.aalperen.pharmacy_application.request.OrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImpTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private MedicineRepository medicineRepository;

    @InjectMocks
    private OrderServiceImp orderService;

    private Order testOrder;
    private Customer testCustomer;
    private Medicine testMedicine;
    private OrderItem testOrderItem;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setFirstName("Test");
        testCustomer.setLastName("Customer");

        testMedicine = new Medicine();
        testMedicine.setId(1L);
        testMedicine.setBrand("Test Medicine");
        testMedicine.setPrice(BigDecimal.valueOf(10.0));
        testMedicine.setQuantity(100);

        testOrder = new Order();
        testOrder.setId(1L);
        testOrder.setCustomer(testCustomer);
        testOrder.setStatus(OrderStatus.PENDING);
        testOrder.setPurchaseDate(LocalDateTime.now());
        testOrder.setTotalPrice(BigDecimal.ZERO);

        testOrderItem = new OrderItem();
        testOrderItem.setId(1L);
        testOrderItem.setOrder(testOrder);
        testOrderItem.setMedicine(testMedicine);
        testOrderItem.setQuantity(2);

        testOrder.setOrderItems(new ArrayList<>(List.of(testOrderItem)));
        testOrder.setTotalPrice(BigDecimal.valueOf(20.0));
    }

    @Test
    void createOrder_ShouldSuccess_WhenValidRequest() {
        OrderRequest request = new OrderRequest();
        request.setCustomerId(1L);
        
        OrderItemRequest itemRequest = new OrderItemRequest();
        itemRequest.setMedicineId(1L);
        itemRequest.setQuantity(2);
        request.setItems(List.of(itemRequest));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));
        when(medicineRepository.findById(1L)).thenReturn(Optional.of(testMedicine));
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(testOrderItem);

        Order result = orderService.createOrder(request);
        

        assertNotNull(result);
        assertEquals(OrderStatus.PENDING, result.getStatus());
        assertEquals(2, result.getOrderItems().size());
        assertEquals(BigDecimal.valueOf(20.0), result.getTotalPrice());
        
        verify(medicineRepository).save(testMedicine);
        verify(orderItemRepository).save(any(OrderItem.class));
    }

    @Test
    void createOrder_ShouldThrowException_WhenCustomerNotFound() {
        OrderRequest request = new OrderRequest();
        request.setCustomerId(1L);
        
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> orderService.createOrder(request));
        
        assertEquals("Müşteri bulunamadı", exception.getErrorMessage());
    }

    @Test
    void createOrder_ShouldThrowException_WhenMedicineNotFound() {
        OrderRequest request = new OrderRequest();
        request.setCustomerId(1L);
        
        OrderItemRequest itemRequest = new OrderItemRequest();
        itemRequest.setMedicineId(1L);
        itemRequest.setQuantity(2);
        request.setItems(List.of(itemRequest));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));
        when(medicineRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> orderService.createOrder(request));
        
        assertEquals("Ürün bulunamadı", exception.getErrorMessage());
    }

    @Test
    void createOrder_ShouldThrowException_WhenNotEnoughStock() {
        OrderRequest request = new OrderRequest();
        request.setCustomerId(1L);
        
        OrderItemRequest itemRequest = new OrderItemRequest();
        itemRequest.setMedicineId(1L);
        itemRequest.setQuantity(200);
        request.setItems(List.of(itemRequest));

        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));
        when(medicineRepository.findById(1L)).thenReturn(Optional.of(testMedicine));

        BusinessException exception = assertThrows(BusinessException.class,
                () -> orderService.createOrder(request));
        
        assertEquals("Yetersiz stok: Test Medicine", exception.getErrorMessage());
    }

    @Test
    void getOrderById_ShouldReturnOrder_WhenExists() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));

        Order result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getOrderById_ShouldThrowException_WhenNotExists() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> orderService.getOrderById(1L));
        
        assertEquals("Order not found with id: 1", exception.getErrorMessage());
    }

    @Test
    void getAllOrders_ShouldReturnOrderList() {
        when(orderRepository.findAll()).thenReturn(List.of(testOrder));

        List<Order> result = orderService.getAllOrders();

        assertEquals(1, result.size());
        assertEquals(testOrder, result.get(0));
    }

    @Test
    void getOrdersByCustomerId_ShouldReturnFilteredOrders() {
        when(orderRepository.findOrdersByCustomerId(1L)).thenReturn(List.of(testOrder));

        List<Order> result = orderService.getOrdersByCustomerId(1L);

        assertEquals(1, result.size());
        assertEquals(testOrder, result.get(0));
    }

    @Test
    void updateOrderStatus_ShouldUpdateStatus() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        Order result = orderService.updateOrderStatus(1L, OrderStatus.COMPLETED);

        assertEquals(OrderStatus.COMPLETED, result.getStatus());
    }

    @Test
    void deleteOrder_ShouldRestoreStock_WhenNotCancelled() {
        testOrder.setStatus(OrderStatus.PENDING);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
        doNothing().when(orderItemRepository).deleteAll(anyList());
        doNothing().when(orderRepository).delete(any(Order.class));

        orderService.deleteOrder(1L);

        verify(medicineRepository).save(testMedicine);
        assertEquals(102, testMedicine.getQuantity());
    }

    @Test
    void deleteOrder_ShouldNotRestoreStock_WhenCancelled() {
        testOrder.setStatus(OrderStatus.CANCELLED);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
        doNothing().when(orderItemRepository).deleteAll(anyList());
        doNothing().when(orderRepository).delete(any(Order.class));

        orderService.deleteOrder(1L);

        verify(medicineRepository, never()).save(testMedicine);
    }

    @Test
    void addItemToOrder_ShouldSuccess_WhenValidRequest() {
        OrderItemRequest itemRequest = new OrderItemRequest();
        itemRequest.setMedicineId(1L);
        itemRequest.setQuantity(3);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
        when(medicineRepository.findById(1L)).thenReturn(Optional.of(testMedicine));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(testOrderItem);
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);

        Order result = orderService.addItemToOrder(1L, itemRequest);

        assertEquals(BigDecimal.valueOf(50.0), result.getTotalPrice());
        verify(medicineRepository).save(testMedicine);
        assertEquals(97, testMedicine.getQuantity());
    }

    @Test
    void removeItemFromOrder_ShouldSuccess_WhenValidItem() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
        when(orderItemRepository.findById(1L)).thenReturn(Optional.of(testOrderItem));
        when(orderRepository.save(any(Order.class))).thenReturn(testOrder);
        doNothing().when(orderItemRepository).delete(any(OrderItem.class));

        Order result = orderService.removeItemFromOrder(1L, 1L);

        assertEquals(0, result.getTotalPrice().compareTo(BigDecimal.ZERO));
        verify(medicineRepository).save(testMedicine);
        assertEquals(102, testMedicine.getQuantity());
    }

}