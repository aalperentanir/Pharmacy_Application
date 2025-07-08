package com.aalperen.pharmacy_application.controller;

import com.aalperen.pharmacy_application.entity.Customer;
import com.aalperen.pharmacy_application.entity.Medicine;
import com.aalperen.pharmacy_application.request.CreateCustomerRequest;
import com.aalperen.pharmacy_application.response.generic.RestApiResponse;
import com.aalperen.pharmacy_application.response.generic.RestResponseStatus;
import com.aalperen.pharmacy_application.service.CustomerService;
import com.aalperen.pharmacy_application.service.MedicineService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v0")
@RequiredArgsConstructor
@Tag(name = "Customer API", description = "Customer yönetimim için API")
public class CustomerController {

    private final CustomerService customerService;
    private final MedicineService medicineService;


    @PostMapping("/customers")
    @Operation(summary = "Customer oluşturma", description = "Yeni bir customer oluşturur.")
    public RestApiResponse<Customer> createCustomer(@RequestBody CreateCustomerRequest req) {

        Customer customer = customerService.createCustomer(req);
        return new RestApiResponse<>(RestResponseStatus.ok(), customer);

    }

    @GetMapping("/customers")
    @Operation(summary = "Bütün customerları getirme", description = "Var olan bütün customerları getirir.")
    public RestApiResponse<List<Customer>> getAllCustomers() {
        return new RestApiResponse<>(RestResponseStatus.ok(), customerService.getAllCustomers());
    }

    @GetMapping("/customers/{customerId}/recommended-products")
    @Operation(summary = "Customerın önerilen medicinelarını getirme", description = "Customer'ın önerilen " +
            "medicinelarını getirir.")
    public RestApiResponse<List<Medicine>> getRecommendedProducts(@PathVariable Long customerId) {
        List<Medicine> recommendedProducts = medicineService.getRecommendedProductsForCustomer(customerId);
        return new RestApiResponse<>(RestResponseStatus.ok(), recommendedProducts);
    }


    @GetMapping("/customers/idNum/{identityNumber}")
    @Operation(summary = "TC. göre customer getirme", description = "Girilen TC.'deki customerı getirir.")
    public RestApiResponse<Customer> getCustomerByIdentityNumber(@PathVariable String identityNumber) {

        Customer customer = customerService.getCustomerByIdentityNumber(identityNumber);
        return new RestApiResponse<>(RestResponseStatus.ok(), customer);
    }


}
