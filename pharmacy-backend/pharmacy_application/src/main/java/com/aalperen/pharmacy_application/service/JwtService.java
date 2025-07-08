package com.aalperen.pharmacy_application.service;

import com.aalperen.pharmacy_application.entity.User;

public interface JwtService {

    String createAuth(User user);
}
