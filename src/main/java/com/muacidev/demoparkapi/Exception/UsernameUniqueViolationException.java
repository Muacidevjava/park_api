package com.muacidev.demoparkapi.Exception;

public class UsernameUniqueViolationException extends RuntimeException {
    public UsernameUniqueViolationException(String message) {
        super(message);
    }
}
