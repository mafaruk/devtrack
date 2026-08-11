package com.devtrack.backend_java.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.devtrack.backend_java.dto.project.ProjectRequest;
import com.devtrack.backend_java.dto.project.ProjectResponse;
import com.devtrack.backend_java.entity.Project;
import com.devtrack.backend_java.entity.User;
import com.devtrack.backend_java.repository.ProjectRepository;
import com.devtrack.backend_java.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ProjectResponse saveProject(ProjectRequest request) {
        if (!StringUtils.hasText(request.name())) {
            throw new IllegalArgumentException("Project name is required");
        }
        if (request.taskCount() < 0) {
            throw new IllegalArgumentException("Task count cannot be negative");
        }

        User owner = userRepository.findByUserName(request.userName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Owner " + request.userName() + " not found"));

        Project project = new Project();
        project.setName(request.name());
        project.setOwner(owner);
        project.setTaskCount(request.taskCount());
            
        project = projectRepository.save(project);

        ProjectResponse projectResponse = new ProjectResponse(project.getId(), project.getName(), project.getOwner().getUserName(), project.getTaskCount(), project.getProjectStatus());

        return projectResponse;
    }

    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(project -> new ProjectResponse(
                        project.getId(),
                        project.getName(),
                        project.getOwner().getUserName(),
                        project.getTaskCount(),
                        project.getProjectStatus()))
                .collect(Collectors.toList());
    }

}
