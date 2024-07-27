package com.example.coursetrackingsystem.ui;

import com.example.coursetrackingsystem.models.Course;
import com.example.coursetrackingsystem.models.Lecturer;
import com.example.coursetrackingsystem.models.MarkSheet;
import com.example.coursetrackingsystem.models.Semester;
import com.example.coursetrackingsystem.repositories.CourseRepository;
import com.example.coursetrackingsystem.repositories.LecturerRepository;
import com.example.coursetrackingsystem.repositories.MarkSheetRepository;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MarkSheetViewController {

    private MarkSheetRepository markSheetRepository;

    private CourseRepository courseRepository;

    private SemesterRepository semesterRepository;

    private LecturerRepository lecturerRepository;

    @FXML
    private ComboBox<String> semesterComboBox;

    @FXML
    private TableView<MarkSheet> markSheetTable;

    @FXML
    private TableColumn<MarkSheet, String> courseNameColumn;

    @FXML
    private TableColumn<MarkSheet, String> lecturerColumn;

    @FXML
    public TableColumn<MarkSheet, Date> dateColumn;

    @FXML
    private TableColumn<MarkSheet, String> submissionStatusColumn;

    @FXML
    private TableColumn<MarkSheet, Integer> totalStudentsColumn;

    @FXML
    private TableColumn<MarkSheet, Integer> studentsMarkedColumn;

    @FXML
    private TableColumn<MarkSheet, Float> averageScoreColumn;

    @FXML
    private TableColumn<MarkSheet, Float> highestScoreColumn;

    @FXML
    private TableColumn<MarkSheet, Float> lowestScoreColumn;

    @FXML
    private ComboBox<String> courseComboBox;

    @FXML
    private ComboBox<String> lecturerComboBox;

    @FXML
    private DatePicker completionDatePicker;

    @FXML
    private ComboBox<String> submissionStatusComboBox;

    @FXML
    private TextField totalStudentsTextField;

    @FXML
    private TextField studentsMarkedTextField;

    @FXML
    private TextField averageScoreTextField;

    @FXML
    private TextField highestScoreTextField;

    @FXML
    private TextField lowestScoreTextField;


    @PostConstruct
    public void init() {
        List<Semester> semesters = semesterRepository.findAll();
        ArrayList<String> list = new ArrayList<String>();
        for(Semester sem:semesters){
            list.add(sem.getSemesterName());
        }
        semesterComboBox.setItems(FXCollections.observableArrayList(list));
        if (!list.isEmpty()) {
            semesterComboBox.setValue(semesters.get(0).getSemesterName());
            loadMarkSheets();
            loadWidgets();
        }

        // Set up courseNameColumn to show course's name
        courseNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MarkSheet, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<MarkSheet, String> param) {
                // Get the Course object from the MarkSheet
                Course course = param.getValue().getCourse();
                // Return the name of the course as an ObservableValue<String>
                return new SimpleStringProperty(course != null ? course.getCourseName() : "");
            }
        });

        // Set up lecturerColumn to show lecturer's name
        lecturerColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MarkSheet, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<MarkSheet, String> param) {
                // Get the Lecturer object from the MarkSheet
                Lecturer lecturer = param.getValue().getLecturer();
                // Return the name of the lecturer as an ObservableValue<String>
                return new SimpleStringProperty(lecturer != null ? lecturer.getName() : "");
            }
        });
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("estimatedCompletionTime"));
        submissionStatusColumn.setCellValueFactory(new PropertyValueFactory<>("submissionStatus"));
        totalStudentsColumn.setCellValueFactory(new PropertyValueFactory<>("totalStudents"));
        studentsMarkedColumn.setCellValueFactory(new PropertyValueFactory<>("studentsMarked"));
        averageScoreColumn.setCellValueFactory(new PropertyValueFactory<>("averageScore"));
        highestScoreColumn.setCellValueFactory(new PropertyValueFactory<>("highestScore"));
        lowestScoreColumn.setCellValueFactory(new PropertyValueFactory<>("lowestScore"));

        submissionStatusColumn.setCellFactory(column -> new TableCell<MarkSheet, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                // Clear the cell if it's empty
                if (empty || status == null) {
                    setText(null);
                    setBackground(null);
                } else {
                    setText(status);
                    switch (status) {
                        case "submitted":
                            setBackground(new Background(new BackgroundFill(Color.web("#a3b18a"), CornerRadii.EMPTY, null)));
                            break;
                        case "not submitted":
                            setBackground(new Background(new BackgroundFill(Color.web("#e9edc9"), CornerRadii.EMPTY, null)));
                            break;
                        case "approved":
                            setBackground(new Background(new BackgroundFill(Color.web("#74c69d"), CornerRadii.EMPTY, null)));
                            break;
                        default:
                            setBackground(null);
                            break;
                    }
                }
            }
        });

        averageScoreColumn.setCellFactory(column -> new TableCell<MarkSheet, Float>() {
            @Override
            protected void updateItem(Float averageScore, boolean empty) {
                super.updateItem(averageScore, empty);

                if (empty || averageScore == null) {
                    setText(null);
                    setBackground(null);
                } else {
                    setText(String.format("%.2f", averageScore));

                    if (averageScore < 50) {
                        setBackground(new Background(new BackgroundFill(Color.web("#e5383b"), CornerRadii.EMPTY, null)));
                    } else if (averageScore < 60) {
                        setBackground(new Background(new BackgroundFill(Color.web("#f9c74f"), CornerRadii.EMPTY, null)));
                    } else if (averageScore < 70) {
                        setBackground(new Background(new BackgroundFill(Color.web("#02c39a"), CornerRadii.EMPTY, null)));
                    } else if (averageScore < 80) {
                        setBackground(new Background(new BackgroundFill(Color.web("#00b4d8"), CornerRadii.EMPTY, null)));
                    } else {
                        setBackground(new Background(new BackgroundFill(Color.web("#2d6a4f"), CornerRadii.EMPTY, null)));
                    }
                }
            }
        });

        highestScoreColumn.setCellFactory(column -> new TableCell<MarkSheet, Float>() {
            @Override
            protected void updateItem(Float highestScore, boolean empty) {
                super.updateItem(highestScore, empty);

                if (empty || highestScore == null) {
                    setText(null);
                    setBackground(null);
                } else {
                    setText(String.format("%.2f", highestScore));
                    setBackground(new Background(new BackgroundFill(Color.web("#4c956c"), CornerRadii.EMPTY, null)));
                }
            }
        });

        lowestScoreColumn.setCellFactory(column -> new TableCell<MarkSheet, Float>() {
            @Override
            protected void updateItem(Float lowestScore, boolean empty) {
                super.updateItem(lowestScore, empty);

                if (empty || lowestScore == null) {
                    setText(null);
                    setBackground(null);
                } else {
                    setText(String.format("%.2f", lowestScore));
                    setBackground(new Background(new BackgroundFill(Color.web("#e5383b"), CornerRadii.EMPTY, null)));
                }
            }
        });
        submissionStatusComboBox.setItems(FXCollections.observableArrayList("submitted", "not submitted", "approved"));

        // Add a listener for row selection
        markSheetTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateWidgets(newValue);
            }
        });
        // Pre-select the first row
        markSheetTable.getSelectionModel().selectFirst();
    }

    public void setRepositories(CourseRepository courseRepository,
                                LecturerRepository lecturerRepository,
                                SemesterRepository semesterRepository,
                                MarkSheetRepository markSheetRepository) {
        this.courseRepository = courseRepository;
        this.lecturerRepository = lecturerRepository;
        this.semesterRepository = semesterRepository;
        this.markSheetRepository = markSheetRepository;
    }

    @FXML
    private void handleSemesterChange() {
        loadMarkSheets();
        loadWidgets();
        markSheetTable.getSelectionModel().selectFirst();
    }

    private void loadWidgets(){
        List<Lecturer> lecturers = lecturerRepository.findAll();
        ArrayList<String> lec_list = new ArrayList<String>();
        for(Lecturer lecturer:lecturers){
            lec_list.add(lecturer.getName());
        }
        lecturerComboBox.setItems(FXCollections.observableArrayList(lec_list));

        Semester semester = semesterRepository.findByName(semesterComboBox.getValue());
        List<Course> courses = courseRepository.findBySemester(semester);
        ArrayList<String> course_list = new ArrayList<String>();
        for(Course course:courses){
            course_list.add(course.getCourseName());
        }
        courseComboBox.setItems(FXCollections.observableArrayList(course_list));
    }

    private void loadMarkSheets() {
        String selectedSemester = semesterComboBox.getSelectionModel().getSelectedItem();
        Semester semester = semesterRepository.findByName(selectedSemester);
        if (semester != null) {
            List<MarkSheet> markSheets = markSheetRepository.findBySemester(semester);
            ObservableList<MarkSheet> markSheetObservableList = FXCollections.observableArrayList(markSheets);
            markSheetTable.setItems(markSheetObservableList);
        }
    }

    private void updateWidgets(MarkSheet newValue) {
        clearFields();

        // Clear and set single item for courseComboBox
        courseComboBox.getItems().clear();
        courseComboBox.getItems().add(newValue.getCourse().getCourseName());
        courseComboBox.setValue(newValue.getCourse().getCourseName());

        // Clear and set single item for lecturerComboBox
        lecturerComboBox.getItems().clear();
        lecturerComboBox.getItems().add(newValue.getLecturer().getName());
        lecturerComboBox.setValue(newValue.getLecturer().getName());

        // Update the ComboBox for Submission Status
        submissionStatusComboBox.getItems().setAll("submitted", "not submitted", "approved");
        submissionStatusComboBox.getSelectionModel().select(newValue.getSubmissionStatus());

        // Set values for other fields
        totalStudentsTextField.setText(String.valueOf(newValue.getTotalStudents()));
        studentsMarkedTextField.setText(String.valueOf(newValue.getStudentsMarked()));
        averageScoreTextField.setText(String.valueOf(newValue.getAverageScore()));
        highestScoreTextField.setText(String.valueOf(newValue.getHighestScore()));
        lowestScoreTextField.setText(String.valueOf(newValue.getLowestScore()));

        // Update Date Picker
        completionDatePicker.setValue(newValue.getEstimatedCompletionTime().toLocalDate());
    }


    @FXML
    protected void clearFields(){
        courseComboBox.getSelectionModel().clearSelection();
        lecturerComboBox.getSelectionModel().clearSelection();
        submissionStatusComboBox.getSelectionModel().clearSelection();
        completionDatePicker.getEditor().clear();
        completionDatePicker.setValue(null);
        totalStudentsTextField.clear();
        studentsMarkedTextField.clear();
        averageScoreTextField.clear();
        highestScoreTextField.clear();
        lowestScoreTextField.clear();
        loadWidgets();
    }

    @FXML
    private void handleSaveMarkSheet() {

        if (semesterComboBox.getValue() == null || courseComboBox.getValue() == null || lecturerComboBox.getValue() == null ||
                submissionStatusComboBox.getValue() == null || completionDatePicker.getValue() == null ||
                totalStudentsTextField.getText().isEmpty() || studentsMarkedTextField.getText().isEmpty() ||
                averageScoreTextField.getText().isEmpty() || highestScoreTextField.getText().isEmpty() || lowestScoreTextField.getText().isEmpty()) {

            showAlert(Alert.AlertType.WARNING, "Incomplete Data", "Please fill in all fields.");
            return;
        }

        Semester semester = semesterRepository.findByName(semesterComboBox.getValue());
        Course selectedCourse = courseRepository.findByName(semester, courseComboBox.getValue());
        Lecturer lecturer = lecturerRepository.findByName(lecturerComboBox.getValue());
        String submissionStatus = submissionStatusComboBox.getValue();
        Date estCompletionDate = Date.valueOf(completionDatePicker.getValue());
        int totalStudents = Integer.parseInt(totalStudentsTextField.getText());
        int studentsMarked = Integer.parseInt(studentsMarkedTextField.getText());
        float averageScore = Float.parseFloat(averageScoreTextField.getText());
        float highestScore = Float.parseFloat(highestScoreTextField.getText());
        float lowestScore = Float.parseFloat(lowestScoreTextField.getText());
        long id = markSheetRepository.getNextMarkSheetId();

        MarkSheet markSheet = new MarkSheet(
                id, selectedCourse, lecturer, semester, submissionStatus, estCompletionDate,
                totalStudents, studentsMarked, averageScore, highestScore, lowestScore
        );

        long marksheetId = markSheetRepository.find(
                semester.getSemesterId(),
                selectedCourse.getCourseId(),
                lecturer.getLecturerId()
        );

        boolean success;
        if (marksheetId != -1) {
            success = markSheetRepository.update(marksheetId, submissionStatus, estCompletionDate,
                    totalStudents, studentsMarked, averageScore, highestScore, lowestScore);
        } else {
            success = markSheetRepository.add(markSheet);
        }

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "MarkSheet saved successfully.");
            loadMarkSheets();
            loadWidgets();
            markSheetTable.getSelectionModel().selectFirst();

        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save MarkSheet.");
        }
    }

    @FXML
    public void dropMarkSheet(){
        if (semesterComboBox.getValue() == null || courseComboBox.getValue() == null || lecturerComboBox.getValue() == null ||
                submissionStatusComboBox.getValue() == null || completionDatePicker.getValue() == null ||
                totalStudentsTextField.getText().isEmpty() || studentsMarkedTextField.getText().isEmpty() ||
                averageScoreTextField.getText().isEmpty() || highestScoreTextField.getText().isEmpty() || lowestScoreTextField.getText().isEmpty()) {

            showAlert(Alert.AlertType.WARNING, "Incomplete Data", "Please fill in all fields.");
            return;
        }

        MarkSheet markSheet = markSheetTable.getSelectionModel().getSelectedItem();
        boolean success= markSheetRepository.drop(markSheet);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "MarkSheet dropped successfully.");
            loadMarkSheets();
            loadWidgets();
            markSheetTable.getSelectionModel().selectFirst();

        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to drop MarkSheet.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleBackToMain() throws IOException {
        Stage stage = (Stage) semesterComboBox.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
        stage.setScene(new Scene(root));
    }
}