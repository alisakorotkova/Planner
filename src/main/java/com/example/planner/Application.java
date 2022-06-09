package com.example.planner;

import com.example.planner.planner.PlannerService;
import com.example.planner.planner.entities.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class Application extends javafx.application.Application {

    public static PlannerService plannerService;

    private static ConfigurableApplicationContext context;


    @Override
    public void start(Stage stage) throws IOException {

        context = SpringApplication.run(Application.class);
        plannerService = context.getBean(PlannerService.class);

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("mainpage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 480);
        stage.setTitle("Planner");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        context.close();
    }

    public static void main(String[] args) {
        launch();
    }
}