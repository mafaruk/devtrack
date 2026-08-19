package com.devtrack.backend_java.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devtrack.backend_java.dto.UserResponse;
import com.devtrack.backend_java.service.UserService;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/user")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/allusers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getMethodName() {
       try {
            List<UserResponse>  userResponses = userService.getAllUser();
            return ResponseEntity.ok(userResponses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{userName}")
    public ResponseEntity<?> getUserByuserName(@PathVariable @NotEmpty String userName){

        UserResponse userResponse = userService.getUserByUsername(userName);
        return ResponseEntity.ok(userResponse);

    }
    
}
