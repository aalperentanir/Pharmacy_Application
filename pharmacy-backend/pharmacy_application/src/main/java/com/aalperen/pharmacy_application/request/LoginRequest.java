package com.aalperen.pharmacy_application.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Kullanıcı giriş isteği modeli")
public class LoginRequest {

    @Schema(description = "Kullanıcı email adresi", example = "user@example.com")
    private String email;

    @Schema(description = "Kullanıcı şifresi (en az 8 karakter)", example = "Password123!")
    private String password;
}
