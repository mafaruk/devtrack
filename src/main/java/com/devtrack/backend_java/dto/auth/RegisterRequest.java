package com.devtrack.backend_java.dto.auth;

import com.devtrack.backend_java.utils.Role;
import com.devtrack.backend_java.validator.annotation.StrongPassword;
import com.devtrack.backend_java.validator.annotation.ValidRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    String email, 
    
    String userName, 

    @NotBlank(message = "Password is required")
    @StrongPassword
    String password, 
    
    @ValidRole
    Role role) {

}
