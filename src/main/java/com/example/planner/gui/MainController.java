package com.example.planner.gui;

import com.example.planner.planner.PlannerService;
import com.example.planner.planner.entities.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    public Button btnAddVert;
    public Button btnSort;
    public HBox scenes;
    public ListView listViewVertex;
    public Label name;


    PlannerService plannerService = com.example.planner.Application.plannerService;
    final ObservableList<Task> tasks = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.listViewVertex.setItems(tasks);
        this.tasks.addAll(this.plannerService.getAllTasks());

        this.listViewVertex.setCellFactory(lv -> {
            ListCell<Task> cell = new ListCell<Task>() {
                @Override
                protected void updateItem(Task item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        return;
                    }
                    TaskListviewItem c = new TaskListviewItem();
                    c.setTask(item);
                    setGraphic(c);
                }
            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    try {
                        Task d = (Task) listViewVertex.getSelectionModel().getSelectedItem();
                        this.taskSelected(e, d);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    e.consume();
                }
            });
            return cell;
        });
    }


    // TODO: сохраним Scene, которую мы прямо сейчас редактируем, чтобы
    // ее можно было обновить или сохранить

    EditTaskScene currentEditTaskScene = null;

    public void taskSelected(MouseEvent mouseEvent, Task selectedItem) throws IOException {

        // if we long to save currently edited Task, we do it here

        EditTaskScene card = new EditTaskScene();
        this.currentEditTaskScene = card;
        card.setEditTask(selectedItem);
        this.scenes.getChildren().clear();
        this.scenes.getChildren().add(card);
    }


    public void btnAddVertPressed(ActionEvent actionEvent) throws IOException {
        System.out.println("btnAddVert was pressed");
        this.taskSelected(null, new Task());

        //this.tasks.add(new Task(8));
//        Task t = this.plannerService.addTask();
//        this.tasks.add(t);

//        this.scenes.getChildren().clear();
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("vertex.fxml"));
//        SubScene scene = new SubScene(fxmlLoader.load(), 130, 220);
//        HBox.setHgrow(scene, Priority.ALWAYS);
//        this.scenes.getChildren().add(scene.getRoot());
    }

    public void btnSortPressed(ActionEvent actionEvent) {
        System.out.println("btnSort was pressed");

    }
}