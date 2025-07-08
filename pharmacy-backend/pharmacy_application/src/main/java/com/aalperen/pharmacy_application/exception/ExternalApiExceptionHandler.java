package com.aalperen.pharmacy_application.exception;


import com.aalperen.pharmacy_application.response.generic.RestApiResponse;
import com.aalperen.pharmacy_application.response.generic.RestResponseStatus;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Hidden //Swagger ui hata vermesin diye eklendi.
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackages = "com.aalperen.pharmacy_application.controller")
public class ExternalApiExceptionHandler {


    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestApiResponse<BaseRestResponse> handleAllException(Exception ex) {
        log.error("handleAllException::{}", ex.getMessage(), ex);
        BaseRestResponse response = new BaseRestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new RestApiResponse<>(RestResponseStatus.ok(), response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BusinessException.class})
    public RestApiResponse<BaseRestResponse> handleBusinessException(BusinessException e) {
        log.error("handleBusinessException: {}", e.getMessage(), e);
        BaseRestResponse response = new BaseRestResponse(e.getCode(),e.getErrorMessage());
        return new RestApiResponse<>(RestResponseStatus.ok(), response);
    }
}
