package com.example.planner.planner.entities;

import com.example.planner.Application;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
public class TaskEdge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public TaskEdge() {

    }

    public TaskEdge(Long sourceId, Long targetId) {
        this.sourceTaskId = sourceId;
        this.targetTaskId = targetId;
    }

    //@ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "source_id")
    private Long sourceTaskId;

    //@ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "target_id")
    private Long targetTaskId;


    public Task getSourceTask() {
        return Application.plannerService.getTaskById(sourceTaskId);
    }

    public Task getTargetTask() {
        return Application.plannerService.getTaskById(targetTaskId);
    }

}
