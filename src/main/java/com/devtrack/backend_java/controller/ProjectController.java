package com.devtrack.backend_java.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.devtrack.backend_java.dto.project.ProjectRequest;
import com.devtrack.backend_java.dto.project.ProjectResponse;
import com.devtrack.backend_java.service.ProjectService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveProject(@RequestBody ProjectRequest entity) {
        try {
            ProjectResponse project = projectService.saveProject(entity);
            return ResponseEntity.ok(project);
        } catch (ResponseStatusException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/allprojects")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllProjects() {
        try{
            List<ProjectResponse> projectResponses =  projectService.getAllProjects();
            return ResponseEntity.ok(projectResponses);
        }catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    

}
