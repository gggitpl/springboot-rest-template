package com.gggitpl.template.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author gggitpl
 */
@Getter
public class ApiException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final GlobalServiceCode globalServiceCode;

    public ApiException(HttpStatus httpStatus, GlobalServiceCode globalServiceCode) {
        super();
        this.httpStatus = httpStatus;
        this.globalServiceCode = globalServiceCode;
    }

    public ApiException(HttpStatus httpStatus, GlobalServiceCode globalServiceCode, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.globalServiceCode = globalServiceCode;
    }
}
