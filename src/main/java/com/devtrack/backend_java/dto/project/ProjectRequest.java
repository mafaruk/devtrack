package com.devtrack.backend_java.dto.project;

import com.devtrack.backend_java.utils.ProjectStatus;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProjectRequest(
    @Nonnull
    String name, 
    
    @PositiveOrZero
    int taskCount, 
    
    String userName, ProjectStatus projectStatus) {

}
