package com.example.planner.planner;

import com.example.planner.Application;
import com.example.planner.gui.MainController;
import com.example.planner.gui.TopSortScene;
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

    public PlannerService() {
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

    public void deleteTask (Task t) {
        for (TaskEdge e : getIngoingTaskEdges(t.getId())) {
            this.edgeRepository.delete(e);
        }
        for (TaskEdge e : getOutgoingTaskEdges(t.getId())) {
            this.edgeRepository.delete(e);
        }

        this.repository.delete(t);
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
    мы хотим знать, какие вершины надо запретить соединять ребрами,
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
        outgoing.add(getTaskById(taskId));
        //outgoing.addAll(getOutgoingTasks(taskId));
        Task o;

        // if we want to detect cycles and delete them
        // let us save task from which we have found cycle
        Map<Long, Long> cycleSources = new HashMap<>();

        while (!outgoing.isEmpty()) {

            //Long prevTaskId = o.getId();
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

        Set<Long> ids = new HashSet<>();

        Queue<Task> ingoing = new LinkedList<>();
        ingoing.add(getTaskById(taskId));
        //ingoing.addAll(getIngoingTasks(taskId));
        Task o;

        // if we want to detect cycles and delete them
        // let us save task from which we have found cycle
        Map<Long, Long> cycleSources = new HashMap<>();

        while (!ingoing.isEmpty()) {

            o = ingoing.remove();

            if (!ids.contains(o.getId())) {
                ids.add(o.getId());

                for (Task t : getIngoingTasks(o.getId())) {
                    System.out.println(t.getId());
                    ingoing.add(t);
                    cycleSources.put(t.getId(), o.getId());
                }
            } else {
                // Cycle situation

                System.out.println(cycleSources.get(o.getId()) + " / " + o.getId());
                removeIngoingTask(cycleSources.get(o.getId()), o.getId());
                //throw new RuntimeException("Congrats! There is a cycle.");
            }

        }

        return ids;
    }



    // --------------------------------

    MainController c;
    TopSortScene f;
    public void notifyController(MainController c) {
        this.c = c;
    }

    public void notifyControllerTS(TopSortScene f) {
        this.f = f;
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
            tasks.add(e.getSourceTask());
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






    // --------------------------------

    // служебное поле для запоминания посещенных вершин
    Map<Long, Boolean> visited;
    ArrayList<Long> answer;

    private void dfs(Task v) throws Exception {

        this.visited.put(v.getId(), true);

        // Для каждой вершины, исходящей из текущей
        for (Task u : Application.plannerService.getOutgoingTasks(v.getId())) {
            // Не был ли я в этой вершине? Если не был, то пойдем!
            if (!this.visited.get(u.getId())) {
                dfs(u);
            } else {
                System.out.println("цикл");
                System.out.println(v.getId() + " " + u.getId());
                System.out.println(Application.plannerService.getTaskById(v.getId()).getLabel());
                System.out.println(Application.plannerService.getTaskById(u.getId()).getLabel());
                throw new Exception("Был найден цикл между задачами " + v.getId() + " и " + u.getId());
            }
        }
        answer.add(v.getId());
    }



    public List<Long> topSort() throws Exception{
        System.out.println("TopSorting");

        // проинициализируем мой массив посещений (да / нет)
        this.visited = new HashMap<>();
        for (Task u : Application.plannerService.getAllTasks()) {
            this.visited.put(u.getId(), false);
        }
        // у меня получился Map: false, false, ..., false
        this.answer = new ArrayList<>();

        // Хочу обойти все компоненты связности графа
        // Все кусочки, из которых он составлен
        // Поэтому насильно попробую пойти из каждой вершины
        for (Task u : Application.plannerService.getAllTasks()) {
            if (!this.visited.get(u.getId())) {
                dfs(u);
            }
        }

        // в answer хранится ответ в перевернутом виде
        // Разверну и покажу


        Collections.reverse(this.answer);
        return this.answer;
    }

}


