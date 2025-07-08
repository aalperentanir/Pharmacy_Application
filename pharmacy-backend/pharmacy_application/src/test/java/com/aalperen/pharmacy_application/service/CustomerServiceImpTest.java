package com.aalperen.pharmacy_application.service;

import com.aalperen.pharmacy_application.entity.Customer;
import com.aalperen.pharmacy_application.entity.Order;
import com.aalperen.pharmacy_application.exception.BusinessException;
import com.aalperen.pharmacy_application.repository.CustomerRepository;
import com.aalperen.pharmacy_application.request.CreateCustomerRequest;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CustomerServiceImpTest {

    @InjectMocks
    private CustomerServiceImp customerServiceImp;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void getAllCustomerTest() {
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Test");
        customer.setLastName("Test");
        customerList.add(customer);
        when(customerRepository.findAll()).thenReturn(customerList);
        List<Customer> resultList = customerServiceImp.getAllCustomers();
        assertEquals(1, resultList.size());
    }

    @Test
    void createCustomer_ShouldSuccess_WhenIdentityNumberNotExists() {
    	CreateCustomerRequest req = new CreateCustomerRequest();
    	req.setIdentityNumber("12345678901");
    	req.setFirstName("Ali");
    	req.setLastName("Tanir");
    	
    	when(customerRepository.existsByIdentityNumber("12345678901")).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        Customer result = customerServiceImp.createCustomer(req);
        
        assertNotNull(result);
        assertEquals("12345678901", result.getIdentityNumber());
        assertEquals("Ali", result.getFirstName());
        assertEquals("Tanir", result.getLastName());
        verify(customerRepository).existsByIdentityNumber("12345678901");
        verify(customerRepository).save(any(Customer.class));
        
        
    }
    
    @Test
    void createCustomer_ShouldThrowException_WhenIdentityNumberExists() {
    	CreateCustomerRequest req = new CreateCustomerRequest();
    	req.setIdentityNumber("12345678901");
    	
    	when(customerRepository.existsByIdentityNumber("12345678901")).thenReturn(true);
   
        BusinessException exception = assertThrows(BusinessException.class, 
                () -> customerServiceImp.createCustomer(req));
        
        assertEquals("Customer already exists", exception.getErrorMessage());
        verify(customerRepository).existsByIdentityNumber("12345678901");
        verify(customerRepository,never()).save(any());
    }
    
    @Test
    void getAllCustomers_ShouldReturnEmptyList_WhenNoCustomersExist() {
    	when(customerRepository.findAll()).thenReturn(Collections.emptyList());
    	
    	List<Customer> result = customerServiceImp.getAllCustomers();
    	
    	assertTrue(result.isEmpty());
    	verify(customerRepository).findAll();
    }
    
    @Test
    void getAllCustomers_ShouldReturnCustomerList_WhenCustomersExist() {
    	Customer customer = new Customer();
    	customer.setId(1L);
   
    	when(customerRepository.findAll()).thenReturn(List.of(customer));
    	
    	List<Customer> result = customerServiceImp.getAllCustomers();
    	
    	assertEquals(1,result.size());
    	assertEquals(1L, result.get(0).getId());
    	verify(customerRepository).findAll();
    	
    }
    
    @Test
    void getCustomerById_ShouldReturnCustomer_WhenCustomerExists() {
    	Long customerId = 1L;
    	Customer customer = new Customer();
    	customer.setId(customerId);
    	
    	when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
    	
    	Customer result = customerServiceImp.getCustomerById(customerId);
    	
    	assertNotNull(result);
    	assertEquals(customerId, result.getId());
    	verify(customerRepository).findById(customerId);
    	
    }
    
    @Test
    void getCustomerById_ShouldThrowException_WhenCustomerNotExists() {
    	Long customerId = 1L;
    	when(customerRepository.findById(customerId)).thenReturn(Optional.empty());
    	
    	BusinessException exception = assertThrows(BusinessException.class,
    			() -> customerServiceImp.getCustomerById(customerId));
    	
    	assertEquals("Customer not found", exception.getErrorMessage());
    	verify(customerRepository).findById(customerId);
    }
    
    
    @Test
    void updateCustomer_ShouldSuccess_WhenValidDataProvided() {
    	Long customerId = 1L;
    	Customer existingCustomer = new Customer();
    	existingCustomer.setId(customerId);
    	existingCustomer.setIdentityNumber("oldId");
    	
    	Customer updateDetails = new Customer();
        updateDetails.setFirstName("New");
        updateDetails.setLastName("Name");
        updateDetails.setIdentityNumber("newId");
        
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.existsByIdentityNumber("newId")).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        Customer result = customerServiceImp.updateCustomer(customerId, updateDetails);
        
        assertEquals("New",result.getFirstName());
        assertEquals("Name",result.getLastName());
        assertEquals("newId",result.getIdentityNumber());
        
        verify(customerRepository).findById(customerId);
        verify(customerRepository).existsByIdentityNumber("newId");
        verify(customerRepository).save(existingCustomer);
        
    	
    }
    
    @Test
    void updateCustomer_ShouldThrowException_WhenIdentityNumberExists() {
    	Long customerId = 1L;
    	Customer existingCustomer = new Customer();
    	existingCustomer.setId(customerId);
    	existingCustomer.setIdentityNumber("oldId");
    	
    	Customer updateDetails = new Customer();
    	updateDetails.setIdentityNumber("existingId");
    	
    	when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
    	when(customerRepository.existsByIdentityNumber("existingId")).thenReturn(true);
    	
    	BusinessException ex = assertThrows(BusinessException.class,
    			() -> customerServiceImp.updateCustomer(customerId, updateDetails));
    	
    	assertEquals("Already exist with the identity number", ex.getErrorMessage());
    	
    	verify(customerRepository).findById(customerId);
        verify(customerRepository).existsByIdentityNumber("existingId");
        verify(customerRepository,never()).save(any());
    	
    }
    
    @Test
    void deleteCustomer_ShouldSuccess_WhenNoOrdersExist() {
    	Long customerId = 1L;
    	Customer customer = new Customer();
    	customer.setId(customerId);
    	customer.setOrders(Collections.emptyList());
    	
    	when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
    	doNothing().when(customerRepository).delete(customer);
    	
    	customerServiceImp.deleteCustomer(customerId);
    	
    	verify(customerRepository).findById(customerId);
    	verify(customerRepository).delete(customer);
    }
    
    @Test
    void deleteCustomer_ShouldThrowException_WhenOrdersExist() {
    	Long customerId = 1L;
    	Customer customer = new Customer();
    	customer.setId(customerId);
        Order order = new Order();
        
        order.setId(1L);
        customer.setOrders(List.of(order));
    	
    	when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
    	
    	BusinessException ex = assertThrows(BusinessException.class,
    			() -> customerServiceImp.deleteCustomer(customerId));
    	
    	assertEquals("This customer has orders first delete orders", ex.getErrorMessage());
    	verify(customerRepository).findById(customerId);
    	verify(customerRepository,never()).delete(any());
    }
    
    @Test
    void getCustomerByIdentityNumber_ShouldReturnCustomer_WhenExists() {
    	
    	String identityNumber = "12345678901";
    	Customer customer = new Customer();
    	customer.setIdentityNumber(identityNumber);
    	
    	when(customerRepository.findByIdentityNumber(identityNumber)).thenReturn(Optional.of(customer));
    	
    	Customer result = customerServiceImp.getCustomerByIdentityNumber(identityNumber);
    	
    	assertNotNull(result);
    	assertEquals(identityNumber, result.getIdentityNumber());
    	verify(customerRepository).findByIdentityNumber(identityNumber);
    	
    	
    	
    }
    
    @Test
    void getCustomerByIdentityNumber_ShouldThrowException_WhenNotExists() {
        String identityNumber = "12345678901";
        when(customerRepository.findByIdentityNumber(identityNumber)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> customerServiceImp.getCustomerByIdentityNumber(identityNumber));
        
        assertEquals("Identity number doesn't exist", exception.getMessage());
        verify(customerRepository).findByIdentityNumber(identityNumber);
    }

}
