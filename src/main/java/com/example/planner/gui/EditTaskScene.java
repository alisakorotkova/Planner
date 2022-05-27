package com.example.planner.gui;

import com.example.planner.Application;
import com.example.planner.planner.entities.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;

public class EditTaskScene extends HBox {

    @FXML
    TextField textEdgeFrom;

    @FXML
    TextField textEdgeTo;

    @FXML
    TextField textAddingName;


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
                // service ->
            }
        });

        this.textEdgeFrom.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                textEdgeTo.requestFocus();
            }
        });
    }

    Task currentTask = null;

    public void setEditTask(Task s) {

        // we gonna save Task to be able to update it later
        this.currentTask = s;

        // TODO: proceed with filling this scene
        this.textAddingName.setText(s.getLabel());

        // TODO: entering the edges and find a way to update a name with the "ENTER"

        // TODO: let us use ComboBox to choose tasks we provide user to choose from
        // see https://www.geeksforgeeks.org/javafx-combobox-with-examples/

        // Algorithm:
        // 1. after we've loaded this scene, ask TaskService which tasks exist
        // 2. set two comboboxes with these available tasks
        // 3. after user adds connection, check if loops are present
        // if not, add the edge

    }

    public void saveTaskBtnClicked() {

        // TODO: save all fields of the scene into the task
        // this.currentTask.setSomething(somethingTextField.getText());

        Application.plannerService.updateTask(this.currentTask);

    }
}
