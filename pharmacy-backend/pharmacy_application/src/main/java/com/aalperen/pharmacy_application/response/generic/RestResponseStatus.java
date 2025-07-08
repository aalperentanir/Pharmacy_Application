package com.aalperen.pharmacy_application.response.generic;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode
@Getter
@Setter
@RequiredArgsConstructor
public class RestResponseStatus implements Serializable {

    @Serial
    private static final long serialVersionUID = -2492665382360008035L;

    private final int code;
    private final String description;

    public static RestResponseStatus ok() {
        return new RestResponseStatus(ReturnCodes.NO_ERROR);
    }

    public RestResponseStatus(String code, String description) {
        this(Integer.valueOf(code), description);
    }

    public RestResponseStatus(ReturnCode returnCode) {
        this(returnCode.intValue(), returnCode.description());
    }
}
