package com.example.planner.planner.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Task {


    /**
     * ...
     */

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    public String label = "Без названия";

    public Task() {}

    Task(String label) {
        this.label = label;
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    @OneToMany(mappedBy = "sourceTask")
    private List<TaskEdge> outgoingTaskEdges;

    @OneToMany(mappedBy = "targetTask")
    private List<TaskEdge> ingoingTaskEdges;

    void addIngoingTask(Task t) {
        this.ingoingTaskEdges.add(new TaskEdge(this, t));
    }

    void addOutgoingTask(Task t) {
        this.outgoingTaskEdges.add(new TaskEdge(t, this));
    }

    List<Task> getIngoingTasks() {
        ArrayList<Task> r  = new ArrayList<>();
        for (TaskEdge e: this.ingoingTaskEdges) {
            r.add(e.getTargetTask());
        }
        return r;
    }

    List<Task> getOutgoingTasks() {
        ArrayList<Task> r  = new ArrayList<>();
        for (TaskEdge e: this.outgoingTaskEdges) {
            r.add(e.getSourceTask());
        }
        return r;
    }

}

