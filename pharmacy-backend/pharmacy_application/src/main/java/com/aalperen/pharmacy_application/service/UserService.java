package com.aalperen.pharmacy_application.service;

import com.aalperen.pharmacy_application.entity.User;

public interface UserService {

    User findUserByToken(String jwt) throws Exception;

    User findUserByEmail(String email) throws Exception;

    User createUser(User user) throws Exception;
}
