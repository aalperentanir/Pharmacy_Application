package com.aalperen.pharmacy_application.response;

import com.aalperen.pharmacy_application.enums.Role;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class AuthResponse implements Serializable {


    @Serial
    private static final long serialVersionUID = -5971072666339294043L;

    private String token;

    private String message;

    private Role role;
}
