package com.aalperen.pharmacy_application.response.generic;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum ReturnCodes implements ReturnCode {

    NO_ERROR(200, "No error", OK),
    BAD_USER_CREDENTIALS(3, "Bad user credentials", BAD_REQUEST),
    USER_NOT_VERIFIED(5, "User not verified", UNAUTHORIZED),
    USER_ALREADY_VERIFIED(6, "User already verified", BAD_REQUEST),
    USER_NOT_FOUND(7, "User not found", NOT_FOUND),
    CUSTOMER_NOT_FOUND(9, "Customer not found", NOT_FOUND),
    NOT_ENOUGH_QUANTITY(10, "Not enough quantity", BAD_REQUEST),
    EMAIL_ALREADY_EXISTS(11, "Email already exists", BAD_REQUEST),
    CUSTOMER_HAS_ORDER(13,"This customer has orders first delete orders",BAD_REQUEST),
    QUANTITY_NEGATIVE(12, "Quantity negative", BAD_REQUEST),
    INSUFFICIENT_STOCK(14,"INSUFFICIENT_STOCK",BAD_REQUEST),
    INVALID_ORDER_ITEM(15,"INVALID_ORDER_ITEM",BAD_REQUEST),
    ALREADY_EXISTS_IDENTITY_NUMBER(15, "Already exists identity number", CONFLICT),
    PRODUCT_NOT_FOUND(16, "Product Not Found", BAD_REQUEST),
    MEDICINE_NOT_FOUND(17, "Medicine Not Found", BAD_REQUEST),
    INTERNAL_SERVER_ERROR(500, "BEKLENMEDİK BİR HATA OLUŞTU!", HttpStatus.INTERNAL_SERVER_ERROR),
    VALIDATION_ERROR(400, "Validation errors exist", BAD_REQUEST);

    private final int code;
    private final String codeString;
    private final String description;
    private final int httpStatus;

    ReturnCodes(int code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.codeString = String.valueOf(code);
        this.description = description;
        this.httpStatus = httpStatus.value();
    }

    @Override
    public String stringValue() {
        return codeString;
    }

    @Override
    public int intValue() {
        return code;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public int httpStatus() {
        return httpStatus;
    }

    @Override
    public boolean isSuccess() {
        return code == NO_ERROR.code;
    }
}
