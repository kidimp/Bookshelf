package com.chous.imageservice.exception;

public class OpenFeignException extends RuntimeException {
    public OpenFeignException(String message, Throwable cause) {
        super(message, cause);
    }
}
