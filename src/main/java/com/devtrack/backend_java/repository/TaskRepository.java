package com.devtrack.backend_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtrack.backend_java.entity.Project;
import com.devtrack.backend_java.entity.Task;
import java.util.List;


public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByProject(Project project);
}
