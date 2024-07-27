package com.example.coursetrackingsystem.ui;

import com.example.coursetrackingsystem.models.Course;
import com.example.coursetrackingsystem.models.Lecturer;
import com.example.coursetrackingsystem.models.Semester;
import com.example.coursetrackingsystem.repositories.CourseRepository;
import com.example.coursetrackingsystem.repositories.LecturerRepository;
import com.example.coursetrackingsystem.repositories.SemesterRepository;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseManagementController {

    private CourseRepository courseRepository;

    private LecturerRepository lecturerRepository;

    private SemesterRepository semesterRepository;

    @FXML
    private ComboBox<String> semesterComboBox;

    @FXML
    public DatePicker semesterStartDate;

    @FXML
    public DatePicker semesterEndDate;

    @FXML
    private TableView<Course> courseTable;

    @FXML
    private TableColumn<Course, Long> courseIdColumn;

    @FXML
    private TableColumn<Course, String> courseNameColumn;

    @FXML
    private TableColumn<Course, String> startDateColumn;

    @FXML
    public DatePicker startDateField;

    @FXML
    private TableColumn<Course, String> lecturerColumn;

    @FXML
    private TextField courseNameField;

    @FXML
    private ComboBox<String> lecturerComboBox;

    @FXML
    public ComboBox<String> courseDropSelection;

    @FXML
    public TextArea courseDetailArea;

    @PostConstruct
    public void init() {
        System.out.println("Init called");
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

        List<Lecturer> lecturers = lecturerRepository.findAll();
        ArrayList<String> lec_list = new ArrayList<String>();
        for(Lecturer lecturer:lecturers){
            lec_list.add(lecturer.getName());
        }
        lecturerComboBox.setItems(FXCollections.observableArrayList(lec_list));

        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
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

    public void setRepositories(CourseRepository courseRepository,
                                LecturerRepository lecturerRepository,
                                SemesterRepository semesterRepository) {
        this.courseRepository = courseRepository;
        this.lecturerRepository = lecturerRepository;
        this.semesterRepository = semesterRepository;
    }

    @FXML
    public void handleSemesterChange() {
        String selectedSemesterName = semesterComboBox.getValue();
        if (selectedSemesterName != null) {
            updateWidgets(selectedSemesterName);
        }
    }

    private void updateWidgets(String semesterName) {
        Semester semester = semesterRepository.findByName(semesterName);
        List<Course> courses = courseRepository.findBySemester(semester);
        ObservableList<Course> courseList = FXCollections.observableArrayList(courses);
        courseTable.setItems(courseList);

        ArrayList<String> course_list = new ArrayList<String>();
        for(Course course:courses){
            course_list.add(course.getCourseName());
        }
        courseDropSelection.setItems(FXCollections.observableArrayList(course_list));
    }

    @FXML
    public void handleSaveCourse() {
        try {
            // Get values from text fields
            String courseName = courseNameField.getText();
            Date startDate = java.sql.Date.valueOf(startDateField.getValue());
            Lecturer lecturer = lecturerRepository.findByName(lecturerComboBox.getValue());
            Semester semester = semesterRepository.findByName(semesterComboBox.getValue());
            Long course_id = courseRepository.getNextCourseId();
            // Create a new Course object
            Course course = new Course(course_id, courseName, startDate, lecturer, semester);

            // Save course to the database
            courseRepository.save(course);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Course Saved");
            alert.setHeaderText(null);
            alert.setContentText("Course has been saved successfully.");
            alert.showAndWait();

            // Clear text fields after saving
            courseNameField.clear();
            lecturerComboBox.getSelectionModel().clearSelection();
            startDateField.getEditor().clear();
            startDateField.setValue(null);
            updateWidgets(semester.getSemesterName());

        } catch (Exception e) {
            // Show error message in case of an exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while saving the course."+ e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void updateCourseDesc(){
        String courseName = courseDropSelection.getValue();
        Semester semester = semesterRepository.findByName(semesterComboBox.getValue());
        Course course = courseRepository.findByName(semester, courseName);
        if(course!=null) {
            courseDetailArea.setText(course.toString());
        }
    }

    @FXML
    public void handleDropCourse() throws IOException{
        try {
            // Get values from text fields
            String courseName = courseDropSelection.getValue();
            Semester semester = semesterRepository.findByName(semesterComboBox.getValue());
            Course course = courseRepository.findByName(semester, courseName);

            // Save course to the database
            courseRepository.drop(semester, course);

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Course Dropped");
            alert.setHeaderText(null);
            alert.setContentText("Course has been dropped successfully.");
            alert.showAndWait();

            // Clear text fields after saving
            courseDetailArea.clear();
            courseDropSelection.getSelectionModel().clearSelection();
            updateWidgets(semester.getSemesterName());

        } catch (Exception e) {
            // Show error message in case of an exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while dropping the course."+ e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void handleBackToMain() throws IOException {
        Stage stage = (Stage) courseNameField.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
        stage.setScene(new Scene(root));
    }

}
