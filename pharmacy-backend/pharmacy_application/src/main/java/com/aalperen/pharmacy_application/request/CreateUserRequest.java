package com.aalperen.pharmacy_application.request;

import com.aalperen.pharmacy_application.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@Schema(description = "Kullanıcı kayıt isteği modeli")
public class CreateUserRequest implements Serializable {


    @Serial
    private static final long serialVersionUID = -4960812340820846297L;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role = Role.EMPLOYEE;
}
