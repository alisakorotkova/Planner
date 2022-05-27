package com.example.planner.gui;

import com.example.planner.planner.entities.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TaskListviewItem extends HBox {


    public Label labelVertName;
    public Label labelVertIndex;
    public Label labelVertIn;
    public Label labelVertOut;

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
        //this.labelVertIndex.setText(task.getInd());
        this.labelVertName.setText(task.getLabel());
        //this.labelVertIn.setText(task.getInd());
        this.labelVertOut.setText(task.getLabel());
    }
}
