package com.devtrack.backend_java.controller;

import org.springframework.web.bind.annotation.RestController;

import com.devtrack.backend_java.dto.project.ProjectRequest;
import com.devtrack.backend_java.dto.project.ProjectResponse;
import com.devtrack.backend_java.exception.ResourceNotFoundException;
import com.devtrack.backend_java.service.ProjectService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/project")
@Validated
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveProject(@RequestBody @Valid ProjectRequest entity) {
        try {
            ProjectResponse project = projectService.saveProject(entity);
            return ResponseEntity.ok(project);
        } catch (ResourceNotFoundException ex) {
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable @PositiveOrZero(message = "Project Id cannot be negative") Long id){

        ProjectResponse projectResponse = projectService.getProjectById(id);
        return ResponseEntity.ok(projectResponse);

    }
    

}
