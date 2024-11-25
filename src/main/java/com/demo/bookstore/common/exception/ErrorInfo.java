package com.demo.bookstore.common.exception;

import java.io.Serializable;

public class ErrorInfo implements Serializable {
    private static final long serialVersionUID = 5671497757543931188L;

    private String error;

    public ErrorInfo(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
