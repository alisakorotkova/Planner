package com.example.planner.gui;

import com.example.planner.Application;
import com.example.planner.planner.entities.Task;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.List;

public class ListViewWithCheckBox extends ListView {

    private ObservableList<Task> items = FXCollections.observableArrayList();

    private boolean forIngoingTasks;

    ListViewWithCheckBox(boolean forIngoingTasks) {

        this.forIngoingTasks = forIngoingTasks;

        super.setCellFactory(CheckBoxListCell.forListView(new Callback<Item, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Item item) {
                return item.onProperty();
            }
        }));

        update();

    }

    private Task currentTask;

    public void setCurrentTask(Task t) {
        this.currentTask = t;
    }

    public void update() {

        List<Task> tasks = Application.plannerService.getAllTasks();

        for (int i = 0; i < tasks.size(); i++) {

            Item item = new Item(tasks.get(i).getLabel(), false);
            item.setTask(tasks.get(i));

            // observe item's on property and display message if it changes:
            item.onProperty().addListener((obs, wasOn, isNowOn) -> {
                //System.out.println(item.getName() + " changed on state from " + wasOn + " to " + isNowOn);

                if (this.forIngoingTasks) {
                    if (isNowOn == true) {
                        Application.plannerService.addIngoingTask(this.currentTask, item.task);
                    } else {
                        Application.plannerService.removeIngoingTask(this.currentTask, item.task);
                    }
                } else {
                    if (isNowOn == true) {
                        Application.plannerService.addOutgoingTask(this.currentTask, item.task);
                    } else {
                        Application.plannerService.removeOutgoingTask(this.currentTask, item.task);
                    }
                }

                //Application.plannerService.updateTask(this.currentTask);

            });

            super.getItems().add(item);
        }
    }

    public static class Item {
        private final StringProperty name = new SimpleStringProperty();
        private final BooleanProperty on = new SimpleBooleanProperty();

        private Task task;

        public Item(String name, boolean on) {
            setName(name);
            setOn(on);
        }

        public final StringProperty nameProperty() {
            return this.name;
        }

        public final String getName() {
            return this.nameProperty().get();
        }

        public final void setName(final String name) {
            this.nameProperty().set(name);
        }

        public final BooleanProperty onProperty() {
            return this.on;
        }

        public final boolean isOn() {
            return this.onProperty().get();
        }

        public final void setOn(final boolean on) {
            this.onProperty().set(on);
        }

        @Override
        public String toString() {
            return getName();
        }

        public void setTask(Task t) {
            this.task = t;
        }

    }

}