package com.aalperen.pharmacy_application.service;

import com.aalperen.pharmacy_application.entity.Customer;
import com.aalperen.pharmacy_application.entity.Medicine;
import com.aalperen.pharmacy_application.enums.SkinType;
import com.aalperen.pharmacy_application.exception.BusinessException;
import com.aalperen.pharmacy_application.repository.CustomerRepository;
import com.aalperen.pharmacy_application.repository.MedicineRepository;
import com.aalperen.pharmacy_application.request.CreateMedicineRequest;
import com.aalperen.pharmacy_application.response.generic.ReturnCodes;
import com.aalperen.pharmacy_application.util.ExceptionUtil;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MedicineServiceImp implements MedicineService {

    private final MedicineRepository medicineRepository;
    private final CustomerRepository customerRepository;


    @Override
    @Transactional
    public Medicine createMedicine(CreateMedicineRequest req) {

        try {
            Medicine medicine = new Medicine();
            medicine.setBrand(req.getBrand());
            medicine.setDescription(req.getDescription());
            medicine.setPrice(req.getPrice());
            medicine.setQuantity(req.getQuantity());
            medicine.setSuitableSkinTypes(req.getSuitableSkinTypes() != null ?
                    req.getSuitableSkinTypes() : new HashSet<>());
            return medicineRepository.save(medicine);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ExceptionUtil.handleException(ex);
            return null;
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Medicine getMedicineById(Long id) {
        try {
            return medicineRepository.findById(id).orElseThrow(() -> new BusinessException("Medicine not found with " +
                    "id: " + id, ReturnCodes.BAD_USER_CREDENTIALS.intValue(), "Medicine not found with id: " + id));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ExceptionUtil.handleException(ex);
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicine> getAllMedicines() {
        try {
            return medicineRepository.findAll();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BusinessException(ReturnCodes.INTERNAL_SERVER_ERROR.description(),
                    ReturnCodes.INTERNAL_SERVER_ERROR.intValue(), ex.getMessage());
        }

    }

    @Override
    @Transactional
    public Medicine updateMedicine(Long id, Medicine medicineDetails) {
        try {
            Medicine medicine = getMedicineById(id);

            medicine.setBrand(medicineDetails.getBrand());
            medicine.setDescription(medicineDetails.getDescription());
            medicine.setPrice(medicineDetails.getPrice());
            medicine.setQuantity(medicineDetails.getQuantity());

            if (medicineDetails.getSuitableSkinTypes() != null) {
                medicine.getSuitableSkinTypes().clear();
                medicine.getSuitableSkinTypes().addAll(medicineDetails.getSuitableSkinTypes());
            }

            return medicineRepository.save(medicine);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BusinessException(ReturnCodes.INTERNAL_SERVER_ERROR.description(),
                    ReturnCodes.INTERNAL_SERVER_ERROR.intValue(), ex.getMessage());
        }

    }

    @Override
    @Transactional
    public void deleteMedicine(Long id) {
        try {
            Medicine medicine = getMedicineById(id);
            medicineRepository.delete(medicine);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BusinessException(ReturnCodes.INTERNAL_SERVER_ERROR.description(),
                    ReturnCodes.INTERNAL_SERVER_ERROR.intValue(), ex.getMessage());
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicine> getMedicineBySkinType(SkinType skinType) {
        try {
            return medicineRepository.findBySuitableSkinTypesContainingIgnoreCase(skinType);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BusinessException(ReturnCodes.INTERNAL_SERVER_ERROR.description(),
                    ReturnCodes.INTERNAL_SERVER_ERROR.intValue(), ex.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicine> getRecommendedProductsForCustomer(Long customerId) {
        try {
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new BusinessException("Customer not found", ReturnCodes.BAD_USER_CREDENTIALS.intValue(), "Customer not found"));

            return medicineRepository.findBySuitableSkinTypesContainingIgnoreCase(customer.getSkinType());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ExceptionUtil.handleException(ex);
            return Collections.emptyList();
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<Medicine> searchMedicine(String keyword) {
        try {
            return medicineRepository.findByBrandContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword,
                    keyword);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BusinessException(ReturnCodes.INTERNAL_SERVER_ERROR.description(),
                    ReturnCodes.INTERNAL_SERVER_ERROR.intValue(), ex.getMessage());
        }

    }

    @Override
    @Transactional
    public Medicine updateStock(Long id, Integer quantityChange) {
        try {
            Medicine medicine = getMedicineById(id);
            int newQuantity = medicine.getQuantity() + quantityChange;

            if (newQuantity < 0) {
                throw new BusinessException("New quantity is negative", ReturnCodes.QUANTITY_NEGATIVE.intValue(),
                        "New quantity is negative");
            }
            medicine.setQuantity(newQuantity);

            return medicineRepository.save(medicine);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BusinessException(ReturnCodes.INTERNAL_SERVER_ERROR.description(),
                    ReturnCodes.INTERNAL_SERVER_ERROR.intValue(), ex.getMessage());
        }
    }


}
