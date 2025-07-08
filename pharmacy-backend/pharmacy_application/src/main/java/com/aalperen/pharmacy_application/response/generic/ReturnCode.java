package com.aalperen.pharmacy_application.response.generic;

import org.springframework.http.HttpStatus;

public interface ReturnCode {
    String stringValue();

    int intValue();

    String description();

    int httpStatus();

    boolean isSuccess();

    default boolean isError() {
        return !isSuccess();
    }

    default boolean isSame(ReturnCode other) {
        if (other == null) {
            return false;
        }
        return other.intValue() == intValue();
    }


    static String getDescription(String errorCode) {
        for (ReturnCodes appError : ReturnCodes.values()) {
            if (appError.stringValue().equals(errorCode)) {
                return appError.description();
            }
        }
        return errorCode;
    }

    static int getHttpStatus(String errorCode) {
        for (ReturnCodes appError : ReturnCodes.values()) {
            if (appError.stringValue().equals(errorCode)) {
                return appError.httpStatus();
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR.value();
    }
}
