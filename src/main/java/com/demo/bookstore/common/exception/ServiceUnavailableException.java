package com.demo.bookstore.common.exception;

import org.springframework.http.HttpStatus;

public class ServiceUnavailableException extends ErrorResponseException {
    public ServiceUnavailableException(String message) {
        super(HttpStatus.SERVICE_UNAVAILABLE, message);
    }
}
