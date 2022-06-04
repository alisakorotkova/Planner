package com.example.planner.planner;

//import com.example.planner.planner.entities.Graph;

import com.example.planner.Application;
import com.example.planner.gui.MainController;
import com.example.planner.planner.entities.Task;
import com.example.planner.planner.entities.TaskEdge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PlannerService {

    @Autowired
    private TaskRepository repository;

    @Autowired
    private TaskEdgeRepository edgeRepository;

//    Map<Long, Task> tasks = new HashMap<>();

    public PlannerService() {
//        List<Task> tasks = this.repository.findAll();
//        for (Task t : tasks) {
//            this.tasks.put(t.getId(), t);
//        }
    }

    public void save() {
//        this.repository.saveAll(tasks.values());
    }

    public List<Task> getAllTasks() {
        return this.repository.findAll();
    }

    public Task addTask(Task t) {
        return this.repository.save(t);
    }

    public Task updateTask(Task t) {
        Task a = this.repository.save(t);
        this.updateTasks();
        return a;
    }

    public Task getTaskById(Long id) {
        return this.repository.getById(id);
    }

    public void addIngoingTask(Long currentId, Long targetId) {
        TaskEdge edge = new TaskEdge(targetId, currentId);
        this.edgeRepository.save(edge);
        this.updateTasks();
    }

    public void addOutgoingTask(Long currentId, Long targetId) {
        TaskEdge edge = new TaskEdge(currentId, targetId);
        this.edgeRepository.save(edge);
        this.updateTasks();
    }

    public void removeIngoingTask(Long currentId, Long targetId) {
        this.edgeRepository.delete(this.edgeRepository.findBySourceTaskIdAndTargetTaskId(targetId, currentId).get(0));
        this.updateTasks();
    }

    public void removeOutgoingTask(Long currentId, Long targetId) {
       this.edgeRepository.delete(this.edgeRepository.findBySourceTaskIdAndTargetTaskId(currentId, targetId).get(0));
       this.updateTasks();
    }

    public boolean taskIsIngoingTo(Long currentId, Long targetId) {
        return this.edgeRepository.findBySourceTaskIdAndTargetTaskId(targetId, currentId).size() != 0;
    }

    public boolean taskIsOutgoingTo(Long currentId, Long targetId) {
        return this.edgeRepository.findBySourceTaskIdAndTargetTaskId(currentId, targetId).size() != 0;
    }

    /*
    TODO: мы хотим знать, какие вершины надо запретить соединять ребрами,
    чтобы не получился цикл, который нам совершенно не нужен

    Какие же это вершины?
    Это те вершины, в которые уже сейчас мы можем дойти

    Например, какие вершины не подходят для того, чтобы стать ingoing для X?
    a -> x -> y
              ^
              z
    Это те, в которые существует путь из x
    т.е. это y
    Доказательство: есть есть уже путь из х в у, значит если мы добавим ребро из у в х, то будет цикл. чтд

    Итак, в этом методе надо получить список номеров (АЙДИ) задач, которые достижимы из текущей
     */
    public Set<Long> getForbiddenIngoingTasks(Long taskId) {

        Set<Long> ids = new HashSet<>();

        Queue<Task> outgoing = new LinkedList<>();
        outgoing.addAll(getOutgoingTasks(taskId));
        Task o = getTaskById(taskId);

        // if we want to detect cycles and delete them
        // let us save task from which we have found cycle
        Map<Long, Long> cycleSources = new HashMap<>();

        while (!outgoing.isEmpty()) {

            Long prevTaskId = o.getId();
            o = outgoing.remove();

            if (!ids.contains(o.getId())) {
                ids.add(o.getId());
                for (Task t : getOutgoingTasks(o.getId())) {
                    outgoing.add(t);
                    cycleSources.put(t.getId(), o.getId());
                }
            } else {
                // Cycle situation
                removeOutgoingTask(cycleSources.get(o.getId()), o.getId());
                //throw new RuntimeException("Congrats! There is a cycle.");
            }

        }

        return ids;
    }

    public Set<Long> getForbiddenOutgoingTasks(Long taskId) {


        // TODO: перепиши аналогично, заменяя аутгоинг на ингоинг
        Set<Long> ids = new HashSet<>();


        return ids;
    }


    // --------------------------------

    MainController c;
    public void notifyController(MainController c) {
        this.c = c;
    }

    private void updateTasks() {
        this.c.initiateUpdate();
    }



    // --------------------------------

    public List<TaskEdge> getIngoingTaskEdges(Long id) {
        return this.edgeRepository.findByTargetTaskId(id);
    }

    public List<TaskEdge> getOutgoingTaskEdges(Long id) {
        return this.edgeRepository.findBySourceTaskId(id);
    }


    public List<Task> getIngoingTasks(Long taskId) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (TaskEdge e : Application.plannerService.getIngoingTaskEdges(taskId)) {
            tasks.add(e.getTargetTask());
        }
        return tasks;
    }

    public List<Task> getOutgoingTasks(Long taskId) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (TaskEdge e : Application.plannerService.getOutgoingTaskEdges(taskId)) {
            tasks.add(e.getTargetTask());
        }
        return tasks;
    }

}


