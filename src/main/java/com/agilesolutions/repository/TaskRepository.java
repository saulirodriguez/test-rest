package com.agilesolutions.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agilesolutions.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

}
