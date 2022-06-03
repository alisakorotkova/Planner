package com.example.planner.planner.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Task {


    public Long getId() {
        return id;
    }

    /**
     * ...
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public String label = "Без названия";

    public Task() {
    }

    Task(String label) {
        this.label = label;
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sourceTask")
    //@Fetch(value = FetchMode.SUBSELECT)
    public List<TaskEdge> outgoingTaskEdges;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "targetTask")
    public List<TaskEdge> ingoingTaskEdges;


    List<Task> getIngoingTasks() {
        ArrayList<Task> r = new ArrayList<>();
        for (TaskEdge e : this.ingoingTaskEdges) {
            r.add(e.getTargetTask());
        }
        return r;
    }

    List<Task> getOutgoingTasks() {
        ArrayList<Task> r = new ArrayList<>();
        for (TaskEdge e : this.outgoingTaskEdges) {
            r.add(e.getSourceTask());
        }
        return r;
    }

}

