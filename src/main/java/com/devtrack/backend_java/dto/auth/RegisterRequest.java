package com.devtrack.backend_java.dto.auth;

import com.devtrack.backend_java.utils.Role;

public record RegisterRequest(String email, String userName, String password, Role role) {

}
