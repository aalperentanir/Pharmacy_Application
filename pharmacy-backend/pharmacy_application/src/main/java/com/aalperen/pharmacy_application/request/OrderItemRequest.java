package com.aalperen.pharmacy_application.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Sipariş öğesi isteği modeli")
public class OrderItemRequest {

    @Schema(description = "İlaç ID'si", example = "1")
    private Long medicineId;

    @Schema(description = "Sipariş adedi", example = "2")
    private int quantity;
}
