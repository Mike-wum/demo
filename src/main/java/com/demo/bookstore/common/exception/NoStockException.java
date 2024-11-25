package com.demo.bookstore.common.exception;

import org.springframework.http.HttpStatus;

public class NoStockException extends ErrorResponseException {
    public NoStockException() {
        super(HttpStatus.FORBIDDEN, "out of stock!");
    }
}
