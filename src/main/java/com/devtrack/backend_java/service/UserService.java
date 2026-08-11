package com.devtrack.backend_java.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devtrack.backend_java.dto.UserResponse;
import com.devtrack.backend_java.dto.project.ProjectResponse;
import com.devtrack.backend_java.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUser(){
        return userRepository.findAll().stream()
        .map(user -> new UserResponse(
            user.getId(), 
            user.getUserName(),
            user.getEmail(), 
            user.getProjects().stream().map(project -> new ProjectResponse(
                        project.getId(),
                        project.getName(),
                        project.getOwner().getUserName(),
                        project.getTaskCount(),
                        project.getProjectStatus()))
                .collect(Collectors.toList()), null, user.getRole()))
        .collect(Collectors.toList());
    }

    

}
