package com.devtrack.backend_java.controller.auth;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.devtrack.backend_java.dto.auth.AuthResponse;
import com.devtrack.backend_java.dto.auth.LoginRequest;
import com.devtrack.backend_java.dto.auth.RegisterRequest;
import com.devtrack.backend_java.entity.User;
import com.devtrack.backend_java.service.auth.AuthService;

import jakarta.validation.Valid;

@RestController
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthService authService, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest entity) {
        User user = new User();
        user.setEmail(entity.email());
        user.setUserName(entity.userName());
        user.setPasswordHash(passwordEncoder.encode(entity.password()));
        user.setRole(entity.role());
        AuthResponse authResponse = authService.register(user);
        return ResponseEntity.ok(authResponse);

    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login( @RequestBody LoginRequest entity) {
        try {
            User user = new User();
            //TODO: login with user name 
            user.setEmail(entity.email());
            user.setPasswordHash(entity.password());
            AuthResponse authResponse = authService.login(user);
            return ResponseEntity.ok(authResponse);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }

    @GetMapping("/api/me")
    public ResponseEntity<?> me(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        System.out.println(user.getEmail() + user.getRole().toString());
        return ResponseEntity.ok(Map.of("email", user.getEmail(), "role", user.getRole()));
    }

}
