package com.example.planner.planner.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TaskEdge {

    @Id
    private Long id;

    public TaskEdge() {

    }

    TaskEdge(Task source, Task target) {
        this.sourceTask = source;
        this.targetTask = target;
    }

    @ManyToOne()
    @JoinColumn(name = "source_id")
    private Task sourceTask;

    @ManyToOne()
    @JoinColumn(name = "target_id")
    private Task targetTask;

    public Task getSourceTask() {
        return sourceTask;
    }

    public Task getTargetTask() {
        return targetTask;
    }

}
