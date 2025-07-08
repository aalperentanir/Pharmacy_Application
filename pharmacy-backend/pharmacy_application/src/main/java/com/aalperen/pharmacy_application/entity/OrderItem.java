package com.aalperen.pharmacy_application.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Data
@Table(name = "orderItems")
public class OrderItem implements Serializable {

    @Serial
    private static final long serialVersionUID = -41726016260383975L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    @Column(name = "quantity")
    private int quantity;


}
