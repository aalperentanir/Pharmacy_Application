package com.aalperen.pharmacy_application.repository;

import com.aalperen.pharmacy_application.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByCustomerId(Long customerId);
}
