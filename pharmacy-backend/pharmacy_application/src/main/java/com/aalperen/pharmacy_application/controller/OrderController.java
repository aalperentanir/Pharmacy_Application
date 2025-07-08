package com.aalperen.pharmacy_application.controller;

import com.aalperen.pharmacy_application.entity.Order;
import com.aalperen.pharmacy_application.enums.OrderStatus;
import com.aalperen.pharmacy_application.request.OrderRequest;
import com.aalperen.pharmacy_application.response.OrderItemResponse;
import com.aalperen.pharmacy_application.response.OrderResponse;
import com.aalperen.pharmacy_application.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v0")
@RequiredArgsConstructor
@Tag(name = "Order API", description = "Order yönetimim için API")
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/orders")
    @Operation(summary = "Order oluşturma", description = "Yeni bir order oluşturur.")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {

        Order order = orderService.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToOrderResponse(order));
    }

    @GetMapping("/orders")
    @Operation(summary = "Bütün orderları getirme", description = "Var olan bütün orderları getirir.")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {

        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders.stream().map(this::mapToOrderResponse).toList());
    }

    @GetMapping("/orders/{id}")
    @Operation(summary = "Id değerine göre order getirme", description = "Girilen id değerine göre order getirir.")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {

        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(mapToOrderResponse(order));
    }

    @DeleteMapping("/orders/{id}")
    @Operation(summary = "Order silme", description = "Var olan bir order'ı siler")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {

        orderService.deleteOrder(id);
        return new ResponseEntity<>("Order deleted", HttpStatus.OK);
    }

    @GetMapping("/orders/customer/{customerId}")
    @Operation(summary = "Customer'ın orderlarını getirme", description = "Customer'ın orderlarını getirir.")
    public ResponseEntity<List<OrderResponse>> getCustomerOrders(@PathVariable Long customerId) {

        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders.stream().map(this::mapToOrderResponse).toList());
    }

    @PutMapping("/orders/{orderId}/status")
    @Operation(summary = "Order status değiştirme", description = "İstenilen bir order'ın status değerini güncelleme " +
            "işlemi")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long orderId,
                                                           @RequestParam OrderStatus orderStatus) {
        Order order = orderService.updateOrderStatus(orderId, orderStatus);
        return ResponseEntity.ok(mapToOrderResponse(order));
    }

    private OrderResponse mapToOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setCustomerId(order.getCustomer().getId());
        response.setCustomerName(order.getCustomer().getFirstName() + " " + order.getCustomer().getLastName());
        response.setTotalPrice(order.getTotalPrice());
        response.setPurchaseDate(order.getPurchaseDate());
        response.setStatus(order.getStatus());

        List<OrderItemResponse> itemResponses = order.getOrderItems().stream().map(item -> {
            OrderItemResponse itemResponse = new OrderItemResponse();
            itemResponse.setId(item.getId());
            itemResponse.setMedicineId(item.getMedicine().getId());
            itemResponse.setMedicineBrand(item.getMedicine().getBrand());
            itemResponse.setUnitPrice(item.getMedicine().getPrice());
            itemResponse.setQuantity(item.getQuantity());
            itemResponse.setSubTotal(item.getMedicine().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            return itemResponse;
        }).toList();

        response.setItems(itemResponses);
        return response;
    }
}
