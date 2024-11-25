package com.demo.bookstore.common.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ErrorResponseException {
    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
