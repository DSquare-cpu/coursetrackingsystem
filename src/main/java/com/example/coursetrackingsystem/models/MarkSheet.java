package com.example.coursetrackingsystem.models;

import java.sql.Date;

public class MarkSheet {

    private Long marksheetId;
    private Course course;
    private Lecturer lecturer;
    private Semester semester;
    private String submissionStatus;
    private Date estimatedCompletionTime;
    private int totalStudents;
    private int studentsMarked;
    private float averageScore;
    private float highestScore;
    private float lowestScore;

    public MarkSheet(Long marksheetId, Course course, Lecturer lecturer, Semester semester, String submissionStatus, Date estimatedCompletionTime, int totalStudents, int studentsMarked, float averageScore, float highestScore, float lowestScore) {
        this.marksheetId = marksheetId;
        this.course = course;
        this.lecturer = lecturer;
        this.semester = semester;
        this.submissionStatus = submissionStatus;
        this.estimatedCompletionTime = estimatedCompletionTime;
        this.totalStudents = totalStudents;
        this.studentsMarked = studentsMarked;
        this.averageScore = averageScore;
        this.highestScore = highestScore;
        this.lowestScore = lowestScore;
    }

    // Getters and Setters
    public Long getMarksheetId() {
        return marksheetId;
    }

    public void setMarksheetId(Long marksheetId) {
        this.marksheetId = marksheetId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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

    public String getSubmissionStatus() {
        return submissionStatus;
    }

    public void setSubmissionStatus(String submissionStatus) {
        this.submissionStatus = submissionStatus;
    }

    public Date getEstimatedCompletionTime() {
        return estimatedCompletionTime;
    }

    public void setEstimatedCompletionTime(Date estimatedCompletionTime) {
        this.estimatedCompletionTime = estimatedCompletionTime;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }

    public int getStudentsMarked() {
        return studentsMarked;
    }

    public void setStudentsMarked(int studentsMarked) {
        this.studentsMarked = studentsMarked;
    }

    public float getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(float averageScore) {
        this.averageScore = averageScore;
    }

    public float getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(float highestScore) {
        this.highestScore = highestScore;
    }

    public float getLowestScore() {
        return lowestScore;
    }

    public void setLowestScore(float lowestScore) {
        this.lowestScore = lowestScore;
    }
}