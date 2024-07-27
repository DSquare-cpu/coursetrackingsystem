package com.example.coursetrackingsystem.models;

import java.util.Date;

public class Test {

    private Long testId;
    private Date testDate;
    private String status;
    private Course course;
    private String name;

    public Test(Long testId, String name, Date testDate, String status, Course course) {
        this.testId = testId;
        this.name = name;
        this.testDate = testDate;
        this.status = status;
        this.course = course;
    }

    // Getters and Setters
    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
