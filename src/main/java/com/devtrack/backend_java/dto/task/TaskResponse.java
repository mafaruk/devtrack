package com.devtrack.backend_java.dto.task;

import com.devtrack.backend_java.utils.TaskStatus;

public record TaskResponse(Long id, String name, String poject, String userName, TaskStatus taskStatus) {

}
