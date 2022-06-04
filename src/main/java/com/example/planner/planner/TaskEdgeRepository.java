package com.example.planner.planner;

import com.example.planner.planner.entities.Task;
import com.example.planner.planner.entities.TaskEdge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskEdgeRepository extends JpaRepository<TaskEdge, Long> {
    List<TaskEdge> findBySourceTaskId(Long id);
    List<TaskEdge> findByTargetTaskId(Long id);

    List<TaskEdge> findBySourceTaskIdAndTargetTaskId(Long sourceId, Long targetId);
}