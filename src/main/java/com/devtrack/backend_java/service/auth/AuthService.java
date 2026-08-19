package com.devtrack.backend_java.service.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.devtrack.backend_java.dto.auth.AuthResponse;
import com.devtrack.backend_java.entity.User;
import com.devtrack.backend_java.exception.InvalidCredentialsException;
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
        if (!StringUtils.hasText(user.getEmail())) {
            throw new InvalidCredentialsException("Email is required");
        }
        if (!StringUtils.hasText(user.getUserName())) {
            throw new InvalidCredentialsException("UserName is required");
        }
        if (!StringUtils.hasText(user.getPasswordHash())) {
            throw new InvalidCredentialsException("Password is required");
        }
        boolean emailExists = StringUtils.hasText(user.getEmail())
                && userRepository.findByEmail(user.getEmail()).isPresent();
        boolean userNameExists = StringUtils.hasText(user.getUserName())
                && userRepository.findByUserName(user.getUserName()).isPresent();

        if (emailExists || userNameExists) {

            throw new InvalidCredentialsException("Email or username already exists");
        } else {
            userRepository.save(user);
            String token = jwtUtils.generateToken(user.getEmail());
            authResponse = new AuthResponse(token);
        }

        return authResponse;
    }

    public AuthResponse login(User user) {

        //TODO: login with user name 
        return userRepository.findByEmail(user.getEmail())
                .map(tempUser -> {
                    if (!passwordEncoder.matches(user.getPasswordHash(), tempUser.getPasswordHash())) {
                        throw new InvalidCredentialsException("Invalid Credentials");
                    }
                    String token = jwtUtils.generateToken(user.getEmail());
                    return new AuthResponse(token);
                })
                .orElseThrow(() -> new InvalidCredentialsException("Invalid Credentials"));
    }

}
