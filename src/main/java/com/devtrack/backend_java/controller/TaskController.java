package com.devtrack.backend_java.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devtrack.backend_java.dto.task.TaskRequest;
import com.devtrack.backend_java.dto.task.TaskResponse;
import com.devtrack.backend_java.exception.ResourceNotFoundException;
import com.devtrack.backend_java.service.TaskService;

import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/task")
@Validated
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveTask(@RequestBody TaskRequest entity) {

        try {
            TaskResponse taskResponse = taskService.saveTask(entity);
            return ResponseEntity.ok(taskResponse);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("{taskId}/assign/{userName}")
    public ResponseEntity<?> assignTask(@PathVariable Long taskId, @PathVariable String userName, @RequestParam(defaultValue = "New") String taskStatus ) {
        try {
            System.out.print( taskId +" "+ userName + " " + taskStatus);
            TaskResponse taskResponse = taskService.assignTaskandNotify(taskId, userName, taskStatus);
            return ResponseEntity.ok(taskResponse);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
       
    }
    

    @GetMapping("/alltasks")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllProjects() {
        try {
            List<TaskResponse> taskResponses = taskService.getAllTask();
            return ResponseEntity.ok(taskResponses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getProjectById(@PathVariable @PositiveOrZero(message = "Task Id cannot be negative") Long taskId){

        TaskResponse taskResponse = taskService.getTaskById(taskId);
        return ResponseEntity.ok(taskResponse);

    }

}
