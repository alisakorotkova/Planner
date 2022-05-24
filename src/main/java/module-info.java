open module com.example.planner {
    requires javafx.controls;
    requires javafx.fxml;

    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.core;
    requires java.persistence;
    requires spring.data.commons;
    requires spring.context;
    requires spring.beans;
    requires java.sql;
    requires spring.data.jpa;
    requires org.hibernate.orm.core;
    requires spring.tx;
//    //requires javafx.fxml;
//
//
//    //opens com.example.planner to javafx.fxml;
//    exports com.example.planner;
//    exports com.example.planner.gui;
//    //opens com.example.planner.gui to javafx.fxml;
}