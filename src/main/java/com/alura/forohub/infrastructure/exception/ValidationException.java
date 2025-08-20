package com.alura.forohub.infrastructure.exception;

public class ValidationException extends RuntimeException {

    public ValidationException(String msg) {
        super(msg);
    }
}