package com.devtrack.backend_java.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private HttpStatus status;

    public ResourceNotFoundException(String message) {
        super(message);
    }

     public ResourceNotFoundException(HttpStatus httpStatus ,String message) {
        super(message);
        this.status = status;
    }
}
