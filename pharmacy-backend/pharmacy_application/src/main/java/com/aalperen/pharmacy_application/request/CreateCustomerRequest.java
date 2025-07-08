package com.aalperen.pharmacy_application.request;

import com.aalperen.pharmacy_application.entity.Order;
import com.aalperen.pharmacy_application.enums.Gender;
import com.aalperen.pharmacy_application.enums.SkinType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Müşteri oluşturma isteği modeli")
public class CreateCustomerRequest {

    @Schema(description = "Müşterinin adı", example = "Ahmet")
    private String firstName;

    @Schema(description = "Müşterinin soyadı", example = "Yılmaz")
    private String lastName;

    @Schema(description = "Cilt tipi (OILY, DRY, COMBINATION, NORMAL, SENSITIVE)", example = "OILY")
    private SkinType skinType;

    @Schema(description = "Cinsiyet (MALE, FEMALE, OTHER)", example = "MALE")
    private Gender gender;

    @Schema(description = "TCKN veya pasaport numarası", example = "12345678901")
    private String identityNumber;

    @Schema(description = "Müşterinin siparişleri (opsiyonel)")
    private List<Order> orders;



}
