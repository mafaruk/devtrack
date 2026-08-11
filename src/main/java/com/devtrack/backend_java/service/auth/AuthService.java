package com.devtrack.backend_java.service.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.devtrack.backend_java.dto.auth.AuthResponse;
import com.devtrack.backend_java.entity.User;
import com.devtrack.backend_java.repository.UserRepository;
import com.devtrack.backend_java.security.JwtUtils;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtils jwtUtils;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public AuthResponse register(User user) {
        AuthResponse authResponse;
        try {
            if (!StringUtils.hasText(user.getEmail())) {
                throw new IllegalArgumentException("Email is required");
            }
            if (!StringUtils.hasText(user.getUserName())) {
                throw new IllegalArgumentException("UserName is required");
            }
            if (!StringUtils.hasText(user.getPasswordHash())) {
                throw new IllegalArgumentException("Password is required");
            }
            boolean emailExists = StringUtils.hasText(user.getEmail())
                    && userRepository.findByEmail(user.getEmail()).isPresent();
            boolean userNameExists = StringUtils.hasText(user.getUserName())
                    && userRepository.findByUserName(user.getUserName()).isPresent();

            if (emailExists || userNameExists) {
                authResponse = new AuthResponse(null, "Email or username already exists");
            } else {
                userRepository.save(user);
                String token = jwtUtils.generateToken(user.getEmail());
                authResponse = new AuthResponse(token, null);
            }

        } catch (RuntimeException e) {
            authResponse = new AuthResponse(null, e.getMessage());
        }
        return authResponse;
    }

    public AuthResponse login(User user) {
        return userRepository.findByEmail(user.getEmail())
                .map(tempUser -> {
                    if (!passwordEncoder.matches(user.getPasswordHash(), tempUser.getPasswordHash())) {
                        return new AuthResponse(null, "Invalid Credentials");
                    }
                    String token = jwtUtils.generateToken(user.getEmail());
                    return new AuthResponse(token, null);
                })
                .orElseGet(() -> new AuthResponse(null, "Invalid Credentials"));
    }

}
