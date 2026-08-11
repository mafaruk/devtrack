package com.devtrack.backend_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devtrack.backend_java.entity.User;
import java.util.Optional;



public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserName(String userName);

}
