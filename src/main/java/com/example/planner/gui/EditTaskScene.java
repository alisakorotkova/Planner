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
import javafx.util.Callback;


public class EditTaskScene extends HBox {

    @FXML
    TextField textAddingName;

    @FXML
    HBox hboxEdges;

    @FXML
    Button btnAdd;

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
                // service ->
            }
        });

//        this.textEdgeFrom.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                textEdgeTo.requestFocus();
//            }
//        });
    }

    Task currentTask = null;

    public void setEditTask(Task s) {

        // we are going to save Task to be able to update it later
        this.currentTask = s;
        //s.setLabel(this.name);

        // TODO: proceed with filling this scene
        this.textAddingName.setText(s.getLabel());

        ListViewWithCheckBox outgoingListView = new ListViewWithCheckBox(false, currentTask);
        this.edgesContainer.getChildren().add(outgoingListView);

        ListViewWithCheckBox ingoingListView = new ListViewWithCheckBox(true, currentTask);
        this.edgesContainer.getChildren().add(ingoingListView);






    }

    public void saveTaskBtnClicked() {

        // TODO: save all fields of the scene into the task
        // this.currentTask.setSomething(somethingTextField.getText());

        this.currentTask.setLabel(this.textAddingName.getText());


        Application.plannerService.updateTask(this.currentTask);
//        Application.plannerService.addTask(this.currentTask);

    }

    public void addTaskBtnClicked() {
        this.currentTask.setLabel(this.textAddingName.getText());
        Application.plannerService.addTask(this.currentTask);
        Application.plannerService.getAllTasks();

    }
}







// TODO: let us use ComboBox to choose tasks we provide user to choose from
// see https://www.geeksforgeeks.org/javafx-combobox-with-examples/

//        javafx.util.Callback<ListView<Task>, ListCell<Task>> f = new Callback<ListView<Task>, ListCell<Task>>() {
//
//        }

//        this.comboBoxFrom = new ComboBox();
//        this.comboBoxFrom.setItems(Application.tasks);
//        comboBoxFrom.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>(){
//
//            @Override
//            public ListCell<Task> call(ListView<Task> p) {
//
//                final ListCell<Task> cell = new ListCell<Task>(){
//
//                    @Override
//                    protected void updateItem(Task t, boolean bln) {
//                        super.updateItem(t, bln);
//
//                        if(t != null){
//                            setText(t.label);
//                        }else{
//                            setText(null);
//                        }
//                    }
//
//                };
//
//                return cell;
//            }
//        });
//        this.comboBoxTo = new ComboBox(Application.tasks);
//        comboBoxTo.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>(){
//
//            @Override
//            public ListCell<Task> call(ListView<Task> p) {
//
//                final ListCell<Task> cell = new ListCell<Task>(){
//
//                    @Override
//                    protected void updateItem(Task t, boolean bln) {
//                        super.updateItem(t, bln);
//
//                        if(t != null){
//                            setText(t.label);
//                        }else{
//                            setText(null);
//                        }
//                    }
//
//                };
//
//                return cell;
//            }
//        });

//        ComboBox from = new ComboBox(tasks);
//        ComboBox to = new ComboBox(tasks);


// Algorithm:
// 1. after we've loaded this scene, ask TaskService which tasks exist
// 2. set two comboboxes with these available tasks
// 3. after user adds connection, check if loops are present
// if not, add the edge
