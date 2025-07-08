package com.aalperen.pharmacy_application.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Sipariş oluşturma isteği modeli")
public class OrderRequest {

    @Schema(description = "Müşteri ID'si",example = "123")
    private Long customerId;

    @Schema(description = "Sipariş öğeleri listesi")
    private List<OrderItemRequest> items;

    @Schema(description = "Toplam fiyat (otomatik hesaplanabilir, opsiyonel)", example = "199.80")
    private BigDecimal totalPrice;
}
