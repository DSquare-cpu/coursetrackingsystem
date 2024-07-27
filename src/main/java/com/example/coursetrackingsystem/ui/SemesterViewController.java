package com.example.coursetrackingsystem.ui;

import com.example.coursetrackingsystem.models.Course;
import com.example.coursetrackingsystem.models.Lecturer;
import com.example.coursetrackingsystem.models.Semester;
import com.example.coursetrackingsystem.repositories.CourseRepository;
import com.example.coursetrackingsystem.repositories.SemesterRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SemesterViewController {

    private CourseRepository courseRepository;
    private SemesterRepository semesterRepository;

    @FXML
    public ComboBox<String> semesterComboBox;
    @FXML
    public DatePicker semesterStartDate;
    @FXML
    public DatePicker semesterEndDate;
    @FXML
    public TextField semesterNameField;
    @FXML
    public DatePicker startDateField;
    @FXML
    public DatePicker endDateField;
    @FXML
    private TableView<Course> courseTable;
    @FXML
    private TableColumn<Course, String> courseNameColumn;
    @FXML
    private TableColumn<Course, String> lecturerColumn;


    public void init() {
        System.out.println("Init called");
        setSemesterComboBox();

        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        // Set up lecturerColumn to show lecturer's name
        lecturerColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Course, String> param) {
                // Get the Lecturer object from the Course
                Lecturer lecturer = param.getValue().getLecturer();
                // Return the name of the lecturer as an ObservableValue<String>
                return new SimpleStringProperty(lecturer != null ? lecturer.getName() : "");
            }
        });
    }

    public void setSemesterComboBox(){
        List<Semester> semesters = semesterRepository.findAll();

        ArrayList<String> list = new ArrayList<String>();
        for(Semester sem:semesters){
            list.add(sem.getSemesterName());
        }
        semesterComboBox.setItems(FXCollections.observableArrayList(list));
        if (!list.isEmpty()) {
            semesterComboBox.setValue(semesters.get(0).getSemesterName());
            updateWidgets(semesters.get(0).getSemesterName());
        }
    }

    public void setRepositories(CourseRepository courseRepository,
                                SemesterRepository semesterRepository) {
        this.courseRepository = courseRepository;
        this.semesterRepository = semesterRepository;
    }

    private void updateWidgets(String semesterName) {
        Semester semester = semesterRepository.findByName(semesterName);

        if(semester !=null) {
            semesterStartDate.setValue(semester.getStartDate().toLocalDate());
            semesterEndDate.setValue(semester.getEndDate().toLocalDate());

            List<Course> courses = courseRepository.findBySemester(semester);
            ObservableList<Course> courseList = FXCollections.observableArrayList(courses);
            courseTable.setItems(courseList);
        }
    }

    public void handleSemesterChange(ActionEvent actionEvent) {
        String semesterName = semesterComboBox.getValue();
        updateWidgets(semesterName);
    }

    @FXML
    public void handleAddSemester(ActionEvent actionEvent) {
        String semesterName = semesterNameField.getText();
        LocalDate startDate = startDateField.getValue();
        LocalDate endDate = endDateField.getValue();

        if (semesterName == null || semesterName.isEmpty() ||
                startDate == null || endDate == null) {

            showAlert(Alert.AlertType.WARNING, "Warning", "Please fill all fields.");
            return;
        }

        Semester semester = new Semester(null, semesterName, Date.valueOf(startDate), Date.valueOf(endDate));
        boolean success = semesterRepository.add(semester);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Semester added successfully.");
            clearFields();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add semester.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        semesterNameField.clear();
        startDateField.setValue(null);
        endDateField.setValue(null);
        setSemesterComboBox();
    }

    @FXML
    public void handleBackToMain() throws IOException {
        Stage stage = (Stage) semesterNameField.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
        stage.setScene(new Scene(root));
    }
}
