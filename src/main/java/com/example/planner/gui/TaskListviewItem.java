package com.example.planner.gui;

import com.example.planner.planner.entities.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TaskListviewItem extends HBox {


    public Label labelVertName;

    TaskListviewItem() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("taskBox.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    void setTask(Task task) {
        this.labelVertName.setText(task.getId() + " " + task.getLabel());
    }

    void setTask(Long id) {
        this.labelVertName.setText(id + "");
    }
}
