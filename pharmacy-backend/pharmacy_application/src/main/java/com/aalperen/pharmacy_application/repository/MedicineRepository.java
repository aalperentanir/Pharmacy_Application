package com.aalperen.pharmacy_application.repository;

import com.aalperen.pharmacy_application.entity.Medicine;
import com.aalperen.pharmacy_application.enums.SkinType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    List<Medicine> findBySuitableSkinTypesContainingIgnoreCase(SkinType skinType);

    List<Medicine> findByBrandContainingIgnoreCase(String brand);

    List<Medicine> findByBrandContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String brand, String description);


}
