package com.example.planner.gui;

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

    public void setEditTask(Task s) {
        this.textAddingName.setText(s.getLabel());

        // TODO: entering the edges and find a way to update a name with the "ENTER"
    }
}
