package com.aalperen.pharmacy_application.controller;

import com.aalperen.pharmacy_application.entity.Medicine;
import com.aalperen.pharmacy_application.enums.SkinType;
import com.aalperen.pharmacy_application.response.generic.RestApiResponse;
import com.aalperen.pharmacy_application.response.generic.RestResponseStatus;
import com.aalperen.pharmacy_application.service.MedicineService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0")
@RequiredArgsConstructor
@Tag(name = "Medicine API", description = "Medicine yönetimim için API")
public class MedicineController {

    private final MedicineService medicineService;

    @GetMapping("/medicines")
    @Operation(summary = "Bütün medicineları getirme", description = "Var olan bütün medicineları getirir.")
    public RestApiResponse<List<Medicine>> getAllMedicines() {

        List<Medicine> medicines = medicineService.getAllMedicines();
        return new RestApiResponse<>(RestResponseStatus.ok(), medicines);
    }

    @GetMapping("/medicines/{id}")
    @Operation(summary = "Id değerine göre medicine getirme", description = "Girilen Id değerine göre medicine " +
            "getirme işlemi.")
    public RestApiResponse<Medicine> getAMedicineById(@PathVariable Long id) {

        Medicine medicine = medicineService.getMedicineById(id);
        return new RestApiResponse<>(RestResponseStatus.ok(), medicine);
    }


    @GetMapping("/medicines/skin-type/{skinType}")
    @Operation(summary = "SkinType değerine göre medicine getirme", description = "Girilen skinType değerine göre " +
            "medicine getirme işlemi.")
    public RestApiResponse<List<Medicine>> findMedicinesBySkinType(@PathVariable SkinType skinType) {

        List<Medicine> medicines = medicineService.getMedicineBySkinType(skinType);
        return new RestApiResponse<>(RestResponseStatus.ok(), medicines);
    }


    @GetMapping("/medicines/search")
    @Operation(summary = "Medicine arama", description = "Girilen metine göre medicine arama işlemi.")
    public RestApiResponse<List<Medicine>> searchMedicines(@RequestParam(required = false) String keyword) {

        List<Medicine> medicines = medicineService.searchMedicine(keyword);
        return new RestApiResponse<>(RestResponseStatus.ok(), medicines);
    }
}
