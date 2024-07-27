package com.example.coursetrackingsystem.models;

public class Lecturer {

    private Long lecturerId;
    private String name;
    private String email;

    public Lecturer(Long lecturerId, String name, String email) {
        this.lecturerId = lecturerId;
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
    public Long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Long lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
