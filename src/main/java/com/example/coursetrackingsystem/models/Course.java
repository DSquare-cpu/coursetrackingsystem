package com.example.coursetrackingsystem.models;

import java.util.Date;

public class Course {
    private Long courseId;
    private String courseName;
    private Date startDate;
    private Lecturer lecturer;
    private Semester semester;

    public Course(Long courseId, String courseName, Date startDate, Lecturer lecturer, Semester semester) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.startDate = startDate;
        this.lecturer = lecturer;
        this.semester = semester;
    }

    // Getters and Setters
    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "ID:\t" + courseId +
                "\n" + courseName + '\'' +
                "\nStart Date:\t" + startDate.toString() +
                "\nLecturer:\t" + lecturer.getName();
    }
}
