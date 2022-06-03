package com.example.planner.planner;

//import com.example.planner.planner.entities.Graph;

import com.example.planner.planner.entities.Task;
import com.example.planner.planner.entities.TaskEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PlannerService {

    @Autowired
    private TaskRepository repository;

    @Autowired
    private TaskEdgeRepository edgeRepository;

    public PlannerService() {

    }

    public List<Task> getAllTasks() {
        return this.repository.findAll();
    }

    public Task addTask(Task t) {
        return this.repository.save(t);
    }

    public Task updateTask(Task t) {
        return this.repository.save(t);
    }

    // TODO: probably, there is a need to update targets also
    public void addIngoingTask(Task current, Task target) {
        TaskEdge edge = new TaskEdge(current, target);
        current.ingoingTaskEdges.add(edge);
        this.edgeRepository.save(edge);
        this.updateTask(current);
    }

    public void addOutgoingTask(Task current, Task target) {
        TaskEdge edge = new TaskEdge(target, current);
        current.outgoingTaskEdges.add(edge);
        this.edgeRepository.save(edge);
        this.updateTask(current);
    }

    public void removeIngoingTask(Task current, Task target) {
        for (int i = 0; i < current.ingoingTaskEdges.size(); i++) {
            if (current.ingoingTaskEdges.get(i).getSourceTask().getId() == target.getId()) {
                this.edgeRepository.delete(current.ingoingTaskEdges.get(i));
                //current.ingoingTaskEdges.remove(current.ingoingTaskEdges.get(i));
            }
        }
    }

    public void removeOutgoingTask(Task current, Task target) {
        for (int i = 0; i < current.outgoingTaskEdges.size(); i++) {
            if (current.outgoingTaskEdges.get(i).getTargetTask().getId() == target.getId()) {

                this.edgeRepository.delete(current.outgoingTaskEdges.get(i));
                //current.outgoingTaskEdges.remove(current.outgoingTaskEdges.get(i));

            }
        }
    }
}


