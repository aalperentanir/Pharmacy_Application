package com.aalperen.pharmacy_application.service;

import com.aalperen.pharmacy_application.entity.Medicine;
import com.aalperen.pharmacy_application.enums.SkinType;
import com.aalperen.pharmacy_application.request.CreateMedicineRequest;

import java.util.List;

public interface MedicineService {

    Medicine createMedicine(CreateMedicineRequest req);

    Medicine getMedicineById(Long id);

    List<Medicine> getAllMedicines();

    Medicine updateMedicine(Long id, Medicine medicineDetails);

    void deleteMedicine(Long id);

    List<Medicine> getMedicineBySkinType(SkinType skinType);

    List<Medicine> getRecommendedProductsForCustomer(Long customerId);

    List<Medicine> searchMedicine(String keyword);

    Medicine updateStock(Long id, Integer quantityChange);
}
