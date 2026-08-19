package com.devtrack.backend_java.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String error, String message, HttpStatus status, LocalDateTime timestamp) {

}
