package com.example.planner.planner.entities;

import com.example.planner.planner.entities.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    ArrayList<Task> graph;

    int V;
    String label;

    Graph(int vertex) {
        this.V = vertex;
        this.graph = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            graph.add(new Task(i));
        }
    }

    Graph() {
        this.graph = new ArrayList<>();
    }

    void addVertex(){
        this.graph.add(new Task(this.graph.size()));
    }


    Task getVertex(int x) {
        return this.graph.get(x);
    }

    ArrayList<Task> getGraph(){
        return this.graph;
    }


    void addEdge(int v, int u) {
        graph.get(v).outgoing.add(graph.get(u));
        graph.get(u).ingoing.add(graph.get(v));
    }

    void printGraph() {
        for (int i = 0; i < V; i++) {
            System.out.println("Project.Vertex " + i + ":  ");
            System.out.println(graph.get(i).print());
        }
    }


    // служебное поле для запоминания посещенных вершин
    Map<Task, Boolean> visited;
    ArrayList<Task> answer;

    private void dfs(Task v) {

        //System.out.println(v.label);
        this.visited.put(v, true);

        // Для каждой вершины, исходящей из текущей
        for (Task u : v.outgoing) {
            // Не был ли я в этой вершине? Если не был, то пойдем!
            if (!this.visited.get(u)) {
                dfs(u);
            }
        }
        answer.add(v);
    }


    // int i - номер вершины, с которой начинаем обход
    public void dfs(int i) {
        System.out.println("DFS from vertex " + i);

        // проинициализируем мой массив посещений (да / нет)
        this.visited = new HashMap<>();
        for (Task u : this.graph) {
            this.visited.put(u, false);
        }
        // у меня получился Map: false, false, ..., false

        Task v = this.graph.get(i);
        dfs(v);
    }


    public void topsort() {
        System.out.println("TopSorting");

        // проинициализируем мой массив посещений (да / нет)
        this.visited = new HashMap<>();
        for (Task u : this.graph) {
            this.visited.put(u, false);
        }
        // у меня получился Map: false, false, ..., false
        this.answer = new ArrayList<>();

        // Хочу обойти все компоненты связности графа
        // Все кусочки, из которых он составлен
        // Поэтому насильно попробую пойти из каждой вершины
        for (Task u : this.graph) {
            if (!this.visited.get(u)) {
                dfs(u);
            }
        }

        // в answer хранится ответ в перевернутом виде
        // Разверну и покажу

        Collections.reverse(this.answer);
        for (Task v : this.answer) {
            System.out.println(v.label);
        }
    }

}

