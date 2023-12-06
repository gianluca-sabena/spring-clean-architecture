package com.example.clean.usecase.exception;

public class CustomerAlreadyExistsException extends RuntimeException {
    public CustomerAlreadyExistsException(final String email) {
        super(email);
    }
}
