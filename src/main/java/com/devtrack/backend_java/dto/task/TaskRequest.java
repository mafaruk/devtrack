package com.devtrack.backend_java.dto.task;

import com.devtrack.backend_java.utils.TaskStatus;

public record TaskRequest(String name, Long project_id, String userName, TaskStatus taskStatus) {

}
