package com.aalperen.pharmacy_application.request;

import com.aalperen.pharmacy_application.enums.SkinType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Schema(description = "İlaç oluşturma isteği modeli")
public class CreateMedicineRequest {
    private String brand;

    private BigDecimal price;

    private Set<SkinType> suitableSkinTypes = new HashSet<>();

    private String description;

    private Integer quantity;



}
