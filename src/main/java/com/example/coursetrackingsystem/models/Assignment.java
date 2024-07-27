package com.example.coursetrackingsystem.models;

import java.util.Date;

public class Assignment {
    private Long assignmentId;
    private Date issueDate;
    private String status;
    private Course course;
    private String name;

    public Assignment(Long assignmentId, String name, Date issueDate, String status, Course course) {
        this.assignmentId = assignmentId;
        this.name = name;
        this.issueDate = issueDate;
        this.status = status;
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getters and Setters
    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
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
}
