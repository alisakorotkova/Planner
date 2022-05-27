package com.example.planner.planner;

import java.util.List;

import com.example.planner.planner.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}