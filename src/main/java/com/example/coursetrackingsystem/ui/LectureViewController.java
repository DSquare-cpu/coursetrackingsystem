package com.example.coursetrackingsystem.ui;

import com.example.coursetrackingsystem.models.Course;
import com.example.coursetrackingsystem.models.Lecturer;
import com.example.coursetrackingsystem.repositories.CourseRepository;
import com.example.coursetrackingsystem.repositories.LecturerRepository;
import com.example.coursetrackingsystem.repositories.SemesterRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

public class LectureViewController {

    private CourseRepository courseRepository;
    private LecturerRepository lecturerRepository;
    private SemesterRepository semesterRepository;

    @FXML
    public TableView<Lecturer> lecturerTable;
    @FXML
    public TableColumn<Lecturer, Long> lecturerIdColumn;
    @FXML
    public TableColumn<Lecturer, String> lecturerNameColumn;
    @FXML
    public TableColumn<Lecturer, String> lecturerEmailColumn;

    @FXML
    public TextArea coursesDescField;
    @FXML
    public TextArea semestersDescField;
    @FXML
    public TextField lecturerNameTextField;
    @FXML
    public TextField lecturerEmailTextField;


    @PostConstruct
    public void init() {
        System.out.println("Init called");
        List<Lecturer> lecturers = lecturerRepository.findAll();

        ObservableList<Lecturer> lecturerList = FXCollections.observableArrayList(lecturers);
        lecturerTable.setItems(lecturerList);

        lecturerIdColumn.setCellValueFactory(new PropertyValueFactory<>("lecturerId"));
        lecturerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lecturerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Add a listener for row selection
        lecturerTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateCourseAndSemesterFields(newValue);
            }
        });

        // Pre-select the first row
        lecturerTable.getSelectionModel().selectFirst();
    }

    public void setRepositories(CourseRepository courseRepository,
                                LecturerRepository lecturerRepository,
                                SemesterRepository semesterRepository) {
        this.courseRepository = courseRepository;
        this.lecturerRepository = lecturerRepository;
    }

    private void updateCourseAndSemesterFields(Lecturer lecturer) {

        coursesDescField.clear();
        semestersDescField.clear();

        // Fetch the courses and semesters for the selected lecturer
        List<Course> courses = courseRepository.findByLecturer(lecturer);

        // Extract course names and semester names
        StringBuilder coursesString = new StringBuilder();
        StringBuilder semestersString = new StringBuilder();

        for (Course course : courses) {
            coursesString.append(course.getCourseName()).append("\n");
            semestersString.append(course.getSemester().getSemesterName()).append("\n");
        }

        // Update the label fields
        coursesDescField.setText(coursesString.toString());
        semestersDescField.setText(semestersString.toString());
    }

    @FXML
    public void handleSaveLecturer() {
        String name = lecturerNameTextField.getText();
        String email = lecturerEmailTextField.getText();

        // Check if fields are empty
        if (name == null || name.trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Name field cannot be empty.");
            return;
        }

        if (email == null || email.trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Email field cannot be empty.");
            return;
        }

        Long id = lecturerRepository.getNextLecturerId();

        // Add lecturer and show success message
        boolean success = lecturerRepository.addLecturer(new Lecturer(id, name, email));

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Lecturer added successfully.");
            init();
        } else {
            showAlert(Alert.AlertType.ERROR, "Failure", "Failed to add lecturer.");
        }
    }

    @FXML
    public void handleBackToMain() throws IOException {
        Stage stage = (Stage) lecturerNameTextField.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
        stage.setScene(new Scene(root));
    }

    // Helper method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
