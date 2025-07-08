package com.aalperen.pharmacy_application.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class BaseRestResponse implements Serializable {


    @Serial
    private static final long serialVersionUID = -3040576603356791514L;

    private int code;
    private String message;
}
