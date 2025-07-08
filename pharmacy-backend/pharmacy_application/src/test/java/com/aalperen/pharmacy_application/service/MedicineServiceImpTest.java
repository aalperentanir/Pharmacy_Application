package com.aalperen.pharmacy_application.service;

import com.aalperen.pharmacy_application.entity.Customer;
import com.aalperen.pharmacy_application.entity.Medicine;
import com.aalperen.pharmacy_application.enums.SkinType;
import com.aalperen.pharmacy_application.exception.BusinessException;
import com.aalperen.pharmacy_application.repository.CustomerRepository;
import com.aalperen.pharmacy_application.repository.MedicineRepository;
import com.aalperen.pharmacy_application.request.CreateMedicineRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicineServiceImpTest {

    @Mock
    private MedicineRepository medicineRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private MedicineServiceImp medicineService;

    private Medicine createTestMedicine(Long id, String brand, BigDecimal price, int quantity, Set<SkinType> skinTypes) {
        Medicine medicine = new Medicine();
        medicine.setId(id);
        medicine.setBrand(brand);
        medicine.setPrice(price);
        medicine.setQuantity(quantity);
        medicine.setSuitableSkinTypes(skinTypes);
        return medicine;
    }

    private CreateMedicineRequest createTestMedicineRequest(String brand, String description, BigDecimal price, int quantity, Set<SkinType> skinTypes) {
        CreateMedicineRequest request = new CreateMedicineRequest();
        request.setBrand(brand);
        request.setDescription(description);
        request.setPrice(price);
        request.setQuantity(quantity);
        request.setSuitableSkinTypes(skinTypes);
        return request;
    }

    @Test
    void createMedicine_ShouldSuccess_WhenValidRequest() {
        Set<SkinType> skinTypes = new HashSet<>(Arrays.asList(SkinType.NORMAL, SkinType.DRY));
        CreateMedicineRequest request = createTestMedicineRequest("Test Brand", "Test Desc", 
            BigDecimal.valueOf(100), 50, skinTypes);
        
        Medicine expectedMedicine = createTestMedicine(1L, "Test Brand", 
            BigDecimal.valueOf(100), 50, skinTypes);
        
        when(medicineRepository.save(any(Medicine.class))).thenReturn(expectedMedicine);

        Medicine result = medicineService.createMedicine(request);

        // Then
        assertNotNull(result);
        assertEquals("Test Brand", result.getBrand());
        assertEquals(2, result.getSuitableSkinTypes().size());
        verify(medicineRepository).save(any(Medicine.class));
    }

    @Test
    void createMedicine_ShouldSetEmptySkinTypes_WhenNotProvided() {
        CreateMedicineRequest request = createTestMedicineRequest("Test Brand", "Test Desc", 
            BigDecimal.valueOf(100), 50, null);
        
        when(medicineRepository.save(any(Medicine.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Medicine result = medicineService.createMedicine(request);

        assertNotNull(result.getSuitableSkinTypes());
        assertTrue(result.getSuitableSkinTypes().isEmpty());
    }

    @Test
    void createMedicine_ShouldThrowBusinessException_WhenRepositoryFails() {
        CreateMedicineRequest request = createTestMedicineRequest("Test Brand", "Test Desc", 
            BigDecimal.valueOf(100), 50, new HashSet<>());
        
        when(medicineRepository.save(any(Medicine.class))).thenThrow(new RuntimeException("DB Error"));

        BusinessException exception = assertThrows(BusinessException.class, 
            () -> medicineService.createMedicine(request));
        
        assertEquals("DB Error", exception.getErrorMessage());
    }

    @Test
    void getMedicineById_ShouldReturnMedicine_WhenExists() {
        Long medicineId = 1L;
        Medicine expected = createTestMedicine(medicineId, "Test", BigDecimal.TEN, 10, new HashSet<>());
        when(medicineRepository.findById(medicineId)).thenReturn(Optional.of(expected));

        Medicine result = medicineService.getMedicineById(medicineId);

        assertNotNull(result);
        assertEquals(medicineId, result.getId());
        verify(medicineRepository).findById(medicineId);
    }

    @Test
    void getMedicineById_ShouldThrowRuntimeException_WhenNotExists() {
        Long medicineId = 1L;
        when(medicineRepository.findById(medicineId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, 
            () -> medicineService.getMedicineById(medicineId));
        
        assertEquals("Medicine not found with id: " + medicineId, exception.getErrorMessage());
    }

    @Test
    void getAllMedicines_ShouldReturnMedicineList_WhenExists() {
        List<Medicine> expectedList = Arrays.asList(
            createTestMedicine(1L, "Med1", BigDecimal.ONE, 5, new HashSet<>()),
            createTestMedicine(2L, "Med2", BigDecimal.TEN, 10, new HashSet<>())
        );
        when(medicineRepository.findAll()).thenReturn(expectedList);

        List<Medicine> result = medicineService.getAllMedicines();

        assertEquals(2, result.size());
        verify(medicineRepository).findAll();
    }

    @Test
    void getAllMedicines_ShouldThrowBusinessException_WhenRepositoryFails() {
        when(medicineRepository.findAll()).thenThrow(new RuntimeException("DB Error"));

        BusinessException exception = assertThrows(BusinessException.class, 
            () -> medicineService.getAllMedicines());
        
        assertEquals("DB Error", exception.getErrorMessage());
    }

    @Test
    void updateMedicine_ShouldUpdateFields_WhenValidData() {
        Long medicineId = 1L;
        Medicine existing = createTestMedicine(medicineId, "Old", BigDecimal.ONE, 5, 
            new HashSet<>(Arrays.asList(SkinType.NORMAL)));
        
        Medicine updateDetails = createTestMedicine(null, "New", BigDecimal.TEN, 10, 
            new HashSet<>(Arrays.asList(SkinType.OILY)));
        
        when(medicineRepository.findById(medicineId)).thenReturn(Optional.of(existing));
        when(medicineRepository.save(any(Medicine.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Medicine result = medicineService.updateMedicine(medicineId, updateDetails);
        
        assertEquals("New", result.getBrand());
        assertEquals(BigDecimal.TEN, result.getPrice());
        assertEquals(10, result.getQuantity());
        assertEquals(1, result.getSuitableSkinTypes().size());
        assertTrue(result.getSuitableSkinTypes().contains(SkinType.OILY));
    }

    @Test
    void deleteMedicine_ShouldSuccess_WhenMedicineExists() {
        Long medicineId = 1L;
        Medicine medicine = createTestMedicine(medicineId, "Test", BigDecimal.ONE, 5, new HashSet<>());
        when(medicineRepository.findById(medicineId)).thenReturn(Optional.of(medicine));
        doNothing().when(medicineRepository).delete(medicine);

        medicineService.deleteMedicine(medicineId);

        verify(medicineRepository).findById(medicineId);
        verify(medicineRepository).delete(medicine);
    }

    @Test
    void getMedicineBySkinType_ShouldReturnFilteredList() {

        SkinType skinType = SkinType.DRY;
        List<Medicine> expectedList = Arrays.asList(
            createTestMedicine(1L, "Med1", BigDecimal.ONE, 5, 
                new HashSet<>(Arrays.asList(SkinType.DRY))),
            createTestMedicine(2L, "Med2", BigDecimal.TEN, 10, 
                new HashSet<>(Arrays.asList(SkinType.DRY, SkinType.NORMAL)))
        );
        when(medicineRepository.findBySuitableSkinTypesContainingIgnoreCase(skinType))
            .thenReturn(expectedList);

        List<Medicine> result = medicineService.getMedicineBySkinType(skinType);

        assertEquals(2, result.size());
        verify(medicineRepository).findBySuitableSkinTypesContainingIgnoreCase(skinType);
    }

    @Test
    void getRecommendedProductsForCustomer_ShouldReturnMatchingProducts() {
        Long customerId = 1L;
        SkinType skinType = SkinType.OILY;
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setSkinType(skinType);
        
        List<Medicine> expectedList = Arrays.asList(
            createTestMedicine(1L, "Med1", BigDecimal.ONE, 5, 
                new HashSet<>(Arrays.asList(SkinType.OILY)))
        );
        
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(medicineRepository.findBySuitableSkinTypesContainingIgnoreCase(skinType))
            .thenReturn(expectedList);


        List<Medicine> result = medicineService.getRecommendedProductsForCustomer(customerId);


        assertEquals(1, result.size());
        assertEquals("Med1", result.get(0).getBrand());
    }

    @Test
    void getRecommendedProductsForCustomer_ShouldThrowException_WhenCustomerNotFound() {

        Long customerId = 1L;
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, 
            () -> medicineService.getRecommendedProductsForCustomer(customerId));
        
        assertEquals("Customer not found", exception.getErrorMessage());
    }

    @Test
    void searchMedicine_ShouldReturnMatchingResults() {
        String keyword = "test";
        List<Medicine> expectedList = Arrays.asList(
            createTestMedicine(1L, "Test Brand", BigDecimal.ONE, 5, new HashSet<>()),
            createTestMedicine(2L, "Another", BigDecimal.TEN, 10, new HashSet<>())
        );
        when(medicineRepository.findByBrandContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword))
            .thenReturn(expectedList);

        List<Medicine> result = medicineService.searchMedicine(keyword);

        assertEquals(2, result.size());
        verify(medicineRepository).findByBrandContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }

    @Test
    void updateStock_ShouldIncreaseQuantity_WhenPositiveChange() {
        Long medicineId = 1L;
        int initialQuantity = 10;
        int quantityChange = 5;
        Medicine medicine = createTestMedicine(medicineId, "Test", BigDecimal.ONE, initialQuantity, new HashSet<>());
        
        when(medicineRepository.findById(medicineId)).thenReturn(Optional.of(medicine));
        when(medicineRepository.save(any(Medicine.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Medicine result = medicineService.updateStock(medicineId, quantityChange);

        assertEquals(initialQuantity + quantityChange, result.getQuantity());
    }

    @Test
    void updateStock_ShouldDecreaseQuantity_WhenNegativeChange() {
        Long medicineId = 1L;
        int initialQuantity = 10;
        int quantityChange = -3;
        Medicine medicine = createTestMedicine(medicineId, "Test", BigDecimal.ONE, initialQuantity, new HashSet<>());
        
        when(medicineRepository.findById(medicineId)).thenReturn(Optional.of(medicine));
        when(medicineRepository.save(any(Medicine.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Medicine result = medicineService.updateStock(medicineId, quantityChange);

        assertEquals(initialQuantity + quantityChange, result.getQuantity());
    }

    @Test
    void updateStock_ShouldThrowBusinessException_WhenNegativeResult() {
        Long medicineId = 1L;
        int initialQuantity = 5;
        int quantityChange = -10;
        Medicine medicine = createTestMedicine(medicineId, "Test", BigDecimal.ONE, initialQuantity, new HashSet<>());
        
        when(medicineRepository.findById(medicineId)).thenReturn(Optional.of(medicine));

        BusinessException exception = assertThrows(BusinessException.class, 
            () -> medicineService.updateStock(medicineId, quantityChange));
        
        assertEquals("New quantity is negative", exception.getErrorMessage());
        assertEquals(medicine.getQuantity(), initialQuantity);
    }
}