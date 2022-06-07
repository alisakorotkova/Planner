package com.example.planner.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class TopSortScene extends HBox {

    @FXML
    ListView lvAnswer;


    public TopSortScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("topsorting.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }







}

