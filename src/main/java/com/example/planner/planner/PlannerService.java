package com.example.planner.planner;

import com.example.planner.planner.entities.Graph;
import com.example.planner.planner.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PlannerService {

    @Autowired
    private TaskRepository repository;



    public PlannerService() {

    }

    public List<Task> getAllTasks() {
        return this.repository.findAll();
    }


    public Task getDefinition(String word) {
        return this.repository.findByWord(word).get(0);
    }



    }

