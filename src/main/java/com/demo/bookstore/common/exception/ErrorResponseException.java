package com.demo.bookstore.common.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponseException extends RuntimeException {
    private HttpStatus code;

    public ErrorResponseException(String message) {
        super(message);
    }

    public ErrorResponseException(HttpStatus code, String message) {
        super(message);
        this.code = code;
    }

    public HttpStatus getCode() {
        return code;
    }
}
