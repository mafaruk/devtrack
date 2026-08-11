package com.devtrack.backend_java.dto.project;

import com.devtrack.backend_java.utils.ProjectStatus;

public record ProjectResponse(Long id, String name, String owner, int taskCount, ProjectStatus projectStatus) {

}
