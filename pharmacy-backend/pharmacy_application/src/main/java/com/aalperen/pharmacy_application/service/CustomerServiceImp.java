package com.aalperen.pharmacy_application.service;

import com.aalperen.pharmacy_application.entity.Customer;
import com.aalperen.pharmacy_application.exception.BusinessException;
import com.aalperen.pharmacy_application.repository.CustomerRepository;
import com.aalperen.pharmacy_application.request.CreateCustomerRequest;
import com.aalperen.pharmacy_application.response.generic.ReturnCodes;
import com.aalperen.pharmacy_application.util.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImp implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public Customer createCustomer(CreateCustomerRequest request) {
        try {
            if (request.getIdentityNumber() != null && customerRepository.existsByIdentityNumber(request.getIdentityNumber())) {
                throw new BusinessException("Customer already exists", ReturnCodes.BAD_USER_CREDENTIALS.intValue(),
                        "Customer already exists");
            }
            Customer customer = new Customer();
            customer.setIdentityNumber(request.getIdentityNumber());
            customer.setFirstName(request.getFirstName());
            customer.setLastName(request.getLastName());
            customer.setGender(request.getGender());
            customer.setSkinType(request.getSkinType());
            customer.setOrders(request.getOrders());
            return customerRepository.save(customer);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ExceptionUtil.handleException(ex);
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {
        try {
            return customerRepository.findById(id).orElseThrow(() -> new BusinessException("Customer not found",
                    ReturnCodes.CUSTOMER_NOT_FOUND.intValue(), "Customer not found"));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ExceptionUtil.handleException(ex);
            return null;
        }
    }

    @Override
    @Transactional
    public Customer updateCustomer(Long id, Customer customerDetails) {
        try {
            Customer customer = getCustomerById(id);
            customer.setFirstName(customerDetails.getFirstName());
            customer.setLastName(customerDetails.getLastName());
            customer.setGender(customerDetails.getGender());
            customer.setSkinType(customerDetails.getSkinType());

            if (customerDetails.getIdentityNumber() != null &&
                    !customerDetails.getIdentityNumber().equals(customer.getIdentityNumber())) {
                if (customerRepository.existsByIdentityNumber(customerDetails.getIdentityNumber())) {
                    throw new BusinessException("Already exist with the identity number",
                            ReturnCodes.ALREADY_EXISTS_IDENTITY_NUMBER.intValue(), "Already exist with the identity " +
                            "number");
                }
                customer.setIdentityNumber(customerDetails.getIdentityNumber());
            }
            return customerRepository.save(customer);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ExceptionUtil.handleException(ex);
            return null;
        }

    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        try {
            Customer customer = getCustomerById(id);
            if (!customer.getOrders().isEmpty()) {
                throw new BusinessException("This customer has orders first delete orders",
                        ReturnCodes.CUSTOMER_HAS_ORDER.intValue(), "This customer has orders first delete orders");
            }
            customerRepository.delete(customer);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ExceptionUtil.handleException(ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerByIdentityNumber(String identityNumber) {
        try {
            return customerRepository.findByIdentityNumber(identityNumber)
                    .orElseThrow(() -> new RuntimeException("Identity number doesn't exist"));

        } catch (Exception ex) {
            log.error(ex.getMessage());
            ExceptionUtil.handleException(ex);
            return null;
        }
    }


}
