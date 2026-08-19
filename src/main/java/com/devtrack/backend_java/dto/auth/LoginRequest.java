package com.devtrack.backend_java.dto.auth;

import com.devtrack.backend_java.validator.annotation.StrongPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    String email, 
    
    @NotBlank(message = "Password is required")
    @StrongPassword
    String password,
    

    String userName
) {

}
