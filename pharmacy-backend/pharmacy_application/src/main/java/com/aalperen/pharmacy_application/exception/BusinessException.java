package com.aalperen.pharmacy_application.exception;


import lombok.Getter;

import java.io.Serial;

import static java.util.Objects.requireNonNullElse;

@Getter
public class BusinessException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final int code;
    private final String description;
    private final String errorMessage;

    public BusinessException(String error, int returnCode, String errorMessage) {
        this(error, returnCode, null, errorMessage);
    }

    public BusinessException(String error, int returnCode, String description, String errorMessage) {
        super(error);
        this.code = returnCode;
        this.description = requireNonNullElse(description, "Hata oluştu.");
        this.errorMessage = errorMessage;
    }
}
