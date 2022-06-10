package com.example.planner.gui;

import com.example.planner.Application;
import com.example.planner.planner.entities.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;


public class EditTaskScene extends HBox {

    @FXML
    TextField textAddingName;

    @FXML
    HBox edgesContainer;

    String name;


    public EditTaskScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("vertex.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.textAddingName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                System.out.println("add task");

                this.name = this.textAddingName.getText();
            }
        });
    }

    Task currentTask = null;

    public void setEditTask(Task s) {
        // we are going to save Task to be able to update it later
        this.currentTask = s;
        //s.setLabel(this.name);
        this.textAddingName.setText(s.getLabel());

        ListViewWithCheckBox ingoingListView = new ListViewWithCheckBox(true, currentTask);
        this.edgesContainer.getChildren().add(ingoingListView);

        ListViewWithCheckBox outgoingListView = new ListViewWithCheckBox(false, currentTask);
        this.edgesContainer.getChildren().add(outgoingListView);


    }

    public void saveTaskBtnClicked() {
        this.currentTask.setLabel(this.textAddingName.getText());
        Application.plannerService.updateTask(this.currentTask);

    }

}

