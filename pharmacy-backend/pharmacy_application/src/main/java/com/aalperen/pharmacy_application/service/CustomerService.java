package com.aalperen.pharmacy_application.service;

import com.aalperen.pharmacy_application.entity.Customer;
import com.aalperen.pharmacy_application.request.CreateCustomerRequest;
import com.aalperen.pharmacy_application.response.CustomerResponse;

import java.util.List;

public interface CustomerService {


    Customer createCustomer(CreateCustomerRequest request);

    List<Customer> getAllCustomers();

    Customer getCustomerById(Long id);

    Customer updateCustomer(Long id,Customer customerDetails);

    void deleteCustomer(Long id);

    Customer getCustomerByIdentityNumber(String identityNumber);

    /*CustomerResponse getCustomerWithOrders(Long customerId);*/
}
