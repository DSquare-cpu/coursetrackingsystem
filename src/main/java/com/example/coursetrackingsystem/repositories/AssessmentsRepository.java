package com.example.coursetrackingsystem.repositories;

import com.example.coursetrackingsystem.database.DatabaseConfig;
import com.example.coursetrackingsystem.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssessmentsRepository {

    public List<Assessment> findByCourse(Course course) {
        List<Assignment> assignments = findAssignmentByCourse(course);
        List<Test> tests = findTestByCourse(course);
        List<Final> finals = findFinalByCourse(course);

        List<Assessment> assessments = new ArrayList<>();
        for(Assignment assignment:assignments){
            String name = assignment.getName();
            Date date = assignment.getIssueDate();
            String status = assignment.getStatus();
            assessments.add(new Assessment(name, date, status));
        }
        for(Test test:tests){
            String name = test.getName();
            Date date = test.getTestDate();
            String status = test.getStatus();
            assessments.add(new Assessment(name, date, status));
        }
        for(Final final_test:finals){
            String name = final_test.getName();
            Date date = final_test.getFinalDate();
            String status = final_test.getStatus();
            assessments.add(new Assessment(name, date, status));
        }
        return assessments;
    }

    public List<Assignment> findAssignmentByCourse(Course course) {
        List<Assignment> assignments = new ArrayList<>();
        String query = "SELECT * FROM assignments WHERE course_id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, course.getCourseId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Assignment assignment = new Assignment(
                        resultSet.getLong("assignment_id"),
                        resultSet.getString("name"),
                        resultSet.getDate("issue_date"),
                        resultSet.getString("status"),
                        course
                );
                assignments.add(assignment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assignments;
    }

    public List<Final> findFinalByCourse(Course course) {
        List<Final> finals = new ArrayList<>();
        String query = "SELECT * FROM finals WHERE course_id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, course.getCourseId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Final final_test = new Final(
                        resultSet.getLong("final_id"),
                        resultSet.getString("name"),
                        resultSet.getDate("final_date"),
                        resultSet.getString("status"),
                        course
                );
                finals.add(final_test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return finals;
    }

    public List<Test> findTestByCourse(Course course) {
        List<Test> tests = new ArrayList<>();
        String query = "SELECT * FROM tests WHERE course_id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, course.getCourseId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Test test = new Test(
                        resultSet.getLong("test_id"),
                        resultSet.getString("name"),
                        resultSet.getDate("test_date"),
                        resultSet.getString("status"),
                        course
                );
                tests.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tests;
    }

}
