package com.aalperen.pharmacy_application.repository;

import com.aalperen.pharmacy_application.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByFirstName(String firstName);

    List<Customer> findByLastName(String lastName);

    Optional<Customer> findByIdentityNumber(String identityNumber);

    boolean existsByIdentityNumber(String identityNumber);
    
    Customer getCustomerById(Long customerId);
}
