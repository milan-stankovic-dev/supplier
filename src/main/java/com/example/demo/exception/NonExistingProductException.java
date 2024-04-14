package com.example.demo.exception;

public class NonExistingProductException extends Exception {
    public NonExistingProductException(String message) {
        super(message);
    }
}
