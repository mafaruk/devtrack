package com.devtrack.backend_java.dto.project;

import com.devtrack.backend_java.utils.ProjectStatus;

public record ProjectRequest(String name, int taskCount, String userName, ProjectStatus projectStatus) {

}
