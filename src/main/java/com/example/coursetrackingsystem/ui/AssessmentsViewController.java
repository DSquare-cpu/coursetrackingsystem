package com.example.coursetrackingsystem.ui;

import com.example.coursetrackingsystem.models.Assessment;
import com.example.coursetrackingsystem.models.Course;
import com.example.coursetrackingsystem.models.Semester;
import com.example.coursetrackingsystem.repositories.AssessmentsRepository;
import com.example.coursetrackingsystem.repositories.CourseRepository;
import com.example.coursetrackingsystem.repositories.LecturerRepository;
import com.example.coursetrackingsystem.repositories.SemesterRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssessmentsViewController {

    private CourseRepository courseRepository;

    private LecturerRepository lecturerRepository;

    private SemesterRepository semesterRepository;

    private AssessmentsRepository assessmentsRepository;

    @FXML
    private ComboBox<String> semesterComboBox;
    @FXML
    private ComboBox<String> courseComboBox;

    @FXML
    public TableView<Assessment> assessmentsTable;
    @FXML
    public TableColumn<Assessment, String> assessmentNameColumn;
    @FXML
    public TableColumn<Assessment, Date> assessmentDateColumn;
    @FXML
    public TableColumn<Assessment, String> assessmentStatusColumn;

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
        }

        handleSemesterChange();

        assessmentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        assessmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        assessmentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    public void setRepositories(CourseRepository courseRepository,
                                LecturerRepository lecturerRepository,
                                SemesterRepository semesterRepository,
                                AssessmentsRepository assessmentsRepository) {
        this.courseRepository = courseRepository;
        this.lecturerRepository = lecturerRepository;
        this.semesterRepository = semesterRepository;
        this.assessmentsRepository = assessmentsRepository;
    }

    public void handleCourseChange(){
        String semesterName = semesterComboBox.getValue();
        String courseName = courseComboBox.getValue();
        Semester semester = semesterRepository.findByName(semesterName);
        Course course = courseRepository.findByName(semester, courseName);

        if(course!=null){
            ArrayList<Assessment> assessments = (ArrayList<Assessment>) assessmentsRepository.findByCourse(course);

            ObservableList<Assessment> assessmentsList = FXCollections.observableArrayList(assessments);
            assessmentsTable.setItems(assessmentsList);
        }
    }

    public void handleSemesterChange(){
        Semester semester = semesterRepository.findByName(semesterComboBox.getValue());
        List<Course> courses = courseRepository.findBySemester(semester);

        ArrayList<String> course_list = new ArrayList<String>();
        for(Course course:courses){
            course_list.add(course.getCourseName());
        }
        courseComboBox.setItems(FXCollections.observableArrayList(course_list));
        if (!course_list.isEmpty()) {
            courseComboBox.setValue(courses.get(0).getCourseName());
        }

        handleCourseChange();
    }

    @FXML
    public void handleBackToMain() throws IOException {
        Stage stage = (Stage) semesterComboBox.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));
        stage.setScene(new Scene(root));
    }
}
