package com.example.clean.usecase.exception;


public class CustomerValidationException extends RuntimeException {
    public CustomerValidationException(final String message) {
        super(message);
    }
}
