package com.example.planner.planner.entities;

import com.example.planner.Application;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


@Entity
@Transactional
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

    public Task(String label) {
        this.label = label;
    }


    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

