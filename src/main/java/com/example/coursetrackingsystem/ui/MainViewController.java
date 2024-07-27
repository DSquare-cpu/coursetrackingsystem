package com.example.coursetrackingsystem.ui;

import com.example.coursetrackingsystem.repositories.AssessmentsRepository;
import com.example.coursetrackingsystem.repositories.CourseRepository;
import com.example.coursetrackingsystem.repositories.LecturerRepository;
import com.example.coursetrackingsystem.repositories.SemesterRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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

        // Create repository instances
        CourseRepository courseRepository = new CourseRepository();
        LecturerRepository lecturerRepository = new LecturerRepository();
        SemesterRepository semesterRepository = new SemesterRepository();

        // Create the controller and inject dependencies
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CourseManagementView.fxml"));
        Parent root = loader.load();
        CourseManagementController controller = loader.getController();
        controller.setRepositories(courseRepository, lecturerRepository, semesterRepository);
        controller.init();

        // Switch scene
        Stage stage = (Stage) manageCoursesButton.getScene().getWindow();
        stage.setScene(new Scene(root));

        System.out.println("Manage Courses clicked");
    }

    @FXML
    private void handleLecturers(ActionEvent event) throws IOException {

        // Create repository instances
        CourseRepository courseRepository = new CourseRepository();
        LecturerRepository lecturerRepository = new LecturerRepository();
        SemesterRepository semesterRepository = new SemesterRepository();

        // Create the controller and inject dependencies
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LecturersView.fxml"));
        Parent root = loader.load();
        LectureViewController controller = loader.getController();
        controller.setRepositories(courseRepository, lecturerRepository, semesterRepository);
        controller.init();

        // Switch scene
        Stage stage = (Stage) manageLecturersButton.getScene().getWindow();
        stage.setScene(new Scene(root));

        System.out.println("Manage Lecturers clicked");
    }

    @FXML
    private void handleAssessments(ActionEvent event) throws IOException {

        // Create repository instances
        CourseRepository courseRepository = new CourseRepository();
        LecturerRepository lecturerRepository = new LecturerRepository();
        SemesterRepository semesterRepository = new SemesterRepository();
        AssessmentsRepository assessmentsRepository = new AssessmentsRepository();

        // Create the controller and inject dependencies
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AssessmentsView.fxml"));
        Parent root = loader.load();
        AssessmentsViewController controller = loader.getController();
        controller.setRepositories(courseRepository, lecturerRepository, semesterRepository, assessmentsRepository);
        controller.init();

        // Switch scene
        Stage stage = (Stage) manageAssignmentsButton.getScene().getWindow();
        stage.setScene(new Scene(root));

        System.out.println("Manage Assessments clicked");
    }


    @FXML
    private void handleMarksheets(ActionEvent event) throws IOException {
        System.out.println("Manage Marksheets clicked");
    }

    @FXML
    private void handleSemesters(ActionEvent event) throws IOException {

        CourseRepository courseRepository = new CourseRepository();
        SemesterRepository semesterRepository = new SemesterRepository();

        // Creating controller and inject dependencies
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SemesterView.fxml"));
        System.out.println("Loaded");
        Parent root = loader.load();
        SemesterViewController controller = loader.getController();
        controller.setRepositories(courseRepository, semesterRepository);
        controller.init();

        // Switching scene
        Stage stage = (Stage) manageSemestersButton.getScene().getWindow();
        stage.setScene(new Scene(root));

        System.out.println("Manage Semester clicked");
    }

}