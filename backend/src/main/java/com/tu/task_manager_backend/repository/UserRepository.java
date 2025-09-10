package com.tu.task_manager_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tu.task_manager_backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}