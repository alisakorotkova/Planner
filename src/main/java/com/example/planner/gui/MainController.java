package com.example.planner.gui;

import com.example.planner.Application;
import com.example.planner.planner.PlannerService;
import com.example.planner.planner.entities.Task;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.TransformationList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;




public class MainController implements Initializable {


    public Button btnAddVert;
    public Button btnDeleteVert;
    public Button btnSort;
    public HBox scenes;
    public ListView listViewVertex;
    public Label name;

    Task selectedTask;

    final ObservableList<Task> tasks = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Application.plannerService.notifyController(this);

        this.listViewVertex.setItems(tasks);
        this.tasks.addAll(Application.plannerService.getAllTasks());

        this.listViewVertex.setCellFactory(lv -> {
            ListCell<Task> cell = new ListCell<Task>() {
                @Override
                protected void updateItem(Task item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
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

        listViewVertex.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            @Override
            public void changed(ObservableValue<? extends Task> observable, Task oldValue, Task newValue) {
                // надо запомнить вершину, которая была выбрана и разблокировать кнопку. при нажатии на кнопку запомненную вершину удалить
                selectedTask = (Task)listViewVertex.getSelectionModel().getSelectedItem();
            }
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
        Task t = Application.plannerService.addTask(new Task("Без названия"));
        initiateUpdate();
        this.taskSelected(null, t);


        //this.tasks.add(new Task(8));
//        Task t = this.plannerService.addTask();
//        this.tasks.add(t);

//        this.scenes.getChildren().clear();
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("vertex.fxml"));
//        SubScene scene = new SubScene(fxmlLoader.load(), 130, 220);
//        HBox.setHgrow(scene, Priority.ALWAYS);
//        this.scenes.getChildren().add(scene.getRoot());
    }

    public void btnDeleteVertPressed(ActionEvent actionEvent) throws IOException {
        System.out.println("btnDeleteVert was pressed");
        Application.plannerService.deleteTask(selectedTask);
        initiateUpdate();

    }

    public void btnSortPressed(ActionEvent actionEvent) throws IOException {
        System.out.println("btnSort was pressed");
        this.scenes.getChildren().clear();
        //FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("topsorting.fxml"));
        SubScene scene = new SubScene(new TopSortScene(), 130, 220);
        HBox.setHgrow(scene, Priority.ALWAYS);
        this.scenes.getChildren().add(scene.getRoot());
    }

    public void initiateUpdate() {

        tasks.clear();
        tasks.addAll(Application.plannerService.getAllTasks());

//        Platform.runLater(() -> {
//            //listViewVertex.getItems().clear();
//
//            listViewVertex.refresh();
//            listViewVertex.setItems(FXCollections.observableArrayList());
//
//            Platform.runLater(() -> {
//                listViewVertex.setItems(tasks);
//
//            });
//
//
//        });
//
//        Thread thread = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//
//
//
//
//            }
//
//        });
//        // don't let thread prevent JVM shutdown
//        thread.start();



    }
}