package com.example.coursetrackingsystem;

import com.example.coursetrackingsystem.database.DatabaseConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class App extends Application {
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try (Connection connection = DatabaseConfig.getConnection()){
            System.out.println("Connected to database");
        } catch(SQLException e) {
            System.out.println(e.getSQLState());
        }
        App.stage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Course Tracking System");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static Stage getStage() {
        return stage;
    }
}