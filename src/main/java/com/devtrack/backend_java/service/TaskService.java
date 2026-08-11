package com.devtrack.backend_java.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.devtrack.backend_java.dto.task.TaskRequest;
import com.devtrack.backend_java.dto.task.TaskResponse;
import com.devtrack.backend_java.entity.Project;
import com.devtrack.backend_java.entity.Task;
import com.devtrack.backend_java.entity.User;
import com.devtrack.backend_java.repository.ProjectRepository;
import com.devtrack.backend_java.repository.TaskRepository;
import com.devtrack.backend_java.repository.UserRepository;
import com.devtrack.backend_java.utils.TaskStatus;

import jakarta.transaction.Transactional;

@Service
public class TaskService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public TaskService(ProjectRepository projectRepository, UserRepository userRepository,
            TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public TaskResponse saveTask(TaskRequest request) {
        if (!StringUtils.hasText(request.name())) {
            throw new IllegalArgumentException("Task name is required");
        }
        if (request.project_id() == null) {
            throw new IllegalArgumentException("Project id is required");
        }

        Task task = new Task();
        if (StringUtils.hasText(request.userName())) {

            User assignee = userRepository.findByUserName(request.userName())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "User " + request.userName() + " not found"));
            task.setAssignee(assignee);
        }

        Project project = projectRepository.findById(request.project_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Project " + request.project_id() + " not found"));

        task.setName(request.name());
        task.setProject(project);
        task.setTaskStatus(request.taskStatus());

        task = taskRepository.save(task);

        var taskCount = taskRepository.findByProject(project).stream().count();
        project.setTaskCount((int)taskCount);
        projectRepository.save(project);

        TaskResponse taskResponse = new TaskResponse(task.getId(), task.getName(),
                 task.getProject().getName(), task.getAssignee()!=null ? task.getAssignee().getUserName() : null, task.getTaskStatus());

        return taskResponse;
    }

    public List<TaskResponse> getAllTask() {
        return taskRepository.findAll().stream().map(task -> new TaskResponse(
                task.getId(),
                task.getName(),
                task.getProject().getName(),
                task.getAssignee()!=null ? task.getAssignee().getUserName() : null,
                task.getTaskStatus())).collect(Collectors.toList());
    }

    @Transactional
    public TaskResponse assignTaskandNotify(Long taskId, String userName, String taskStatus) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Owner " + userName + " not found"));
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Task not found"));

        task.setAssignee(user);
        task.setTaskStatus(TaskStatus.valueOf(taskStatus));
        taskRepository.save(task);

        return new TaskResponse(task.getId(), task.getName(), task.getProject().getName(),
                task.getAssignee().getUserName(), task.getTaskStatus());
    }

}
