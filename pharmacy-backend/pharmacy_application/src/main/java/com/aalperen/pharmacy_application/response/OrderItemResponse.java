package com.aalperen.pharmacy_application.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponse {

    private Long id;

    private Long medicineId;

    private String medicineBrand;

    private BigDecimal unitPrice;

    private int quantity;

    private BigDecimal subTotal;


}
