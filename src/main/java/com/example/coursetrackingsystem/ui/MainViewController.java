package com.example.coursetrackingsystem.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MainViewController {

    @FXML
    private Button manageCoursesButton;

    @FXML
    private Button manageLecturersButton;

    @FXML
    private Button manageAssignmentsButton;

    @FXML
    private Button manageMarksheetsButton;

    @FXML
    private Button manageSemestersButton;


    @FXML
    private void handleCourses(ActionEvent event) throws IOException {
        System.out.println("Manage Courses clicked");
    }

    @FXML
    private void handleLecturers(ActionEvent event) throws IOException {
        System.out.println("Manage Lecturers clicked");
    }

    @FXML
    private void handleAssessments(ActionEvent event) throws IOException {
        System.out.println("Manage Assessments clicked");
    }

    @FXML
    private void handleMarksheets(ActionEvent event) throws IOException {
        System.out.println("Manage Marksheets clicked");
    }

    @FXML
    private void handleSemesters(ActionEvent event) throws IOException {
        System.out.println("Manage Semester clicked");
    }

}