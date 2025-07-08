package com.aalperen.pharmacy_application.repository;

import com.aalperen.pharmacy_application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String username);
}
