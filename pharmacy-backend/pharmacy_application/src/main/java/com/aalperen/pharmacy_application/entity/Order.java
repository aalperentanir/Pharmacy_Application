package com.aalperen.pharmacy_application.entity;

import com.aalperen.pharmacy_application.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 2876005775915064437L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @Column(name = "total_amount")
    private Long totalAmount;

    @Column(name = "status")
    private OrderStatus status = OrderStatus.PENDING;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderItem> orderItems = new ArrayList<>();


}
