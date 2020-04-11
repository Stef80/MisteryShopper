package com.example.misteryshopper.exception;

public class InvalidParamsException extends RuntimeException {

    public InvalidParamsException() {
        this("invalid params");
    }

    public InvalidParamsException(String message) {
        super(message);
    }
}
