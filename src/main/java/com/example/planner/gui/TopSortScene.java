package com.example.planner.gui;

import com.example.planner.Application;
import com.example.planner.planner.entities.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TopSortScene extends HBox implements Initializable {

    @FXML
    ListView lvAnswer;

    @FXML
    Label labelN;


    public TopSortScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("topsorting.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    final ObservableList<Task> tasksTS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Application.plannerService.notifyControllerTS(this);

        try {
            this.lvAnswer.setItems(tasksTS);
            List<Long> ids = Application.plannerService.topSort();
            for (Long id : ids) {
                this.tasksTS.add(Application.plannerService.getTaskById(id));
            }
        } catch(Exception e){
            this.labelN.setText(e.getMessage());

        }



        this.lvAnswer.setCellFactory(lv -> {
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
                    //c.setTask(item.getId());
                    setGraphic(c);
                }
            };
            return cell;
        });
    }
}

