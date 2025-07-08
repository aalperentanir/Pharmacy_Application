package com.aalperen.pharmacy_application.controller.admin;

import com.aalperen.pharmacy_application.entity.Customer;
import com.aalperen.pharmacy_application.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/v0")
@RequiredArgsConstructor
@Tag(name="Admin Customer API", description = "Admin Customer yönetimim için API")
public class AdminCustomerController {

    private final CustomerService customerService;

    @PutMapping("/customers/{id}")
    @Operation(summary = "Customer'ın değerlerini güncelleme",description = "Seçilen customer'ın değerleri güncellenir")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer,
                                                   @PathVariable Long id) {

        Customer updateCustomer = customerService.updateCustomer(id,customer);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }


    @DeleteMapping("/customers/{id}")
    @Operation(summary = "Customer silme",description = "Var olan bir customer'ı siler")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id)  {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>("Customer deleted", HttpStatus.OK);
    }
}
