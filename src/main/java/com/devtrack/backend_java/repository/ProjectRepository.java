package com.devtrack.backend_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtrack.backend_java.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

}
