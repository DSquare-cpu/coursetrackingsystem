package com.example.coursetrackingsystem.models;

import java.util.Date;

public class Final {
    private Long finalId;
    private String name;
    private Date finalDate;
    private String status;
    private Course course;

    public Final(Long finalId, String name, Date finalDate, String status, Course course) {
        this.finalId = finalId;
        this.name = name;
        this.finalDate = finalDate;
        this.status = status;
        this.course = course;
    }

    // Getters and Setters
    public Long getFinalId() {
        return finalId;
    }

    public void setFinalId(Long finalId) {
        this.finalId = finalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
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
