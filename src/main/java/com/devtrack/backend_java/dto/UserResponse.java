package com.devtrack.backend_java.dto;

import java.util.List;

import com.devtrack.backend_java.dto.project.ProjectResponse;
import com.devtrack.backend_java.dto.task.TaskResponse;
import com.devtrack.backend_java.utils.Role;


public record UserResponse(Long id, String userName, String email, List<ProjectResponse> projects, List<TaskResponse> task, Role role) {

}
