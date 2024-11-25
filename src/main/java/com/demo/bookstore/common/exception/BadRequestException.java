package com.demo.bookstore.common.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ErrorResponseException {
    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
