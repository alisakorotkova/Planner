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
import java.util.Set;

public class ListViewWithCheckBox extends ListView {

    private boolean forIngoingTasks;

    ListViewWithCheckBox(boolean forIngoingTasks, Task currentTask) {

        this.forIngoingTasks = forIngoingTasks;
        this.currentTask = currentTask;

        super.setCellFactory(CheckBoxListCell.forListView(new Callback<Item, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Item item) {
                return item.onProperty();
            }
        }));

        update();

    }

    private Task currentTask;

    //public void setCurrentTask(Task t) {
//        this.currentTask = t;
//    }

    public void update() {

        this.getItems().clear();

        List<Task> tasks = Application.plannerService.getAllTasks();
        Long currentTaskId = currentTask.getId();

        Set<Long> forbiddenTasks;
        if (forIngoingTasks) {
            forbiddenTasks = Application.plannerService.getForbiddenIngoingTasks(currentTaskId);
        } else {
            forbiddenTasks = Application.plannerService.getForbiddenOutgoingTasks(currentTaskId);
        }

        for (int i = 0; i < tasks.size(); i++) {

            Item item;

            boolean isSelected;
            if (forIngoingTasks) {
                isSelected = Application.plannerService.taskIsIngoingTo(currentTaskId, tasks.get(i).getId());
            } else {
                isSelected = Application.plannerService.taskIsOutgoingTo(currentTaskId, tasks.get(i).getId());
            }

            if ((!forbiddenTasks.contains(tasks.get(i).getId()) || isSelected) && (tasks.get(i).getId() != currentTaskId)) {
                item = new Item(tasks.get(i).getLabel(), isSelected);
                item.setTask(tasks.get(i));
            } else {
                continue;
            }

            item.onProperty().addListener((obs, wasOn, isNowOn) -> {
                if (this.forIngoingTasks) {
                    if (isNowOn == true) {
                        Application.plannerService.addIngoingTask(currentTaskId, item.task.getId());
                    } else {
                        Application.plannerService.removeIngoingTask(currentTaskId, item.task.getId());
                    }
                } else {
                    if (isNowOn == true) {
                        Application.plannerService.addOutgoingTask(currentTaskId, item.task.getId());
                    } else {
                        Application.plannerService.removeOutgoingTask(currentTaskId, item.task.getId());
                    }
                }

                update();
                //Application.plannerService.updateTask(this.currentTask);

            });

            this.getItems().add(item);
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