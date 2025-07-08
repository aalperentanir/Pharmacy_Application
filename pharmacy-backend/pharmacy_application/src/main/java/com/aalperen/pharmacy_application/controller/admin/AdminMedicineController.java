package com.aalperen.pharmacy_application.controller.admin;

import com.aalperen.pharmacy_application.entity.Medicine;
import com.aalperen.pharmacy_application.request.CreateMedicineRequest;
import com.aalperen.pharmacy_application.service.MedicineService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/v0")
@RequiredArgsConstructor
@Tag(name="Admin Medicine API", description = "Admin Medicine yönetimim için API")
public class AdminMedicineController {

    private final MedicineService medicineService;

    @PostMapping("/medicines")
    @Operation(summary = "Medicine Oluşturma",description = "Yeni medicine oluşturur.")
    public ResponseEntity<Medicine> createMedicine(@RequestBody CreateMedicineRequest req) {
        Medicine medicine = medicineService.createMedicine(req);
        return new ResponseEntity<>(medicine, HttpStatus.CREATED);
    }

    @PutMapping("/medicines/{id}")
    @Operation(summary = "Medicine Güncelleme",description = "Seçilen medicine güncellenir")
    public ResponseEntity<Medicine> updateMedicine(@PathVariable Long id,
                                                   @RequestBody Medicine medicine) {
        Medicine updateMedicine = medicineService.updateMedicine(id,medicine);
        return new ResponseEntity<>(updateMedicine, HttpStatus.OK);
    }

    @DeleteMapping("/medicines/{id}")
    @Operation(summary = "Medicine Silme",description = "Seçilen medicine silinir")
    public ResponseEntity<String> deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
        return new ResponseEntity<>("Medicine deleted successfully", HttpStatus.OK);
    }

}
