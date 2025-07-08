package com.aalperen.pharmacy_application.response.generic;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;
import java.net.http.HttpResponse;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class RestApiResponse<T> implements Serializable {


    @Serial
    private static final long serialVersionUID = -5587935670247593075L;

    private final T data;

    public RestApiResponse(T data) {
        this(RestResponseStatus.ok(), data);
    }

    public RestApiResponse(RestResponseStatus status) {
        this(status, null);
    }

    public RestApiResponse(RestResponseStatus status, T data) {
        this.data = data;
    }

}
