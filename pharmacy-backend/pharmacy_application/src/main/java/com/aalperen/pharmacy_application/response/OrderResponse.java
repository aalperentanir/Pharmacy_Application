package com.aalperen.pharmacy_application.response;

import com.aalperen.pharmacy_application.enums.OrderStatus;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1695983934255050823L;

    private Long id;

    private Long customerId;

    private String customerName;

    private BigDecimal totalPrice;

    private LocalDateTime purchaseDate;

    private OrderStatus status;

    private List<OrderItemResponse> items;

    public OrderResponse() {
    }

    public OrderResponse(Long id, BigDecimal totalPrice, LocalDateTime purchaseDate, Long totalAmount, OrderStatus status) {
    }


}
