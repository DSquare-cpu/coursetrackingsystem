package com.example.coursetrackingsystem.repositories;

import com.example.coursetrackingsystem.database.DatabaseConfig;
import com.example.coursetrackingsystem.models.Semester;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SemesterRepository {

    public List<Semester> findAll() {
        List<Semester> semesters = new ArrayList<>();
        String query = "SELECT * FROM semesters";

        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Semester semester = new Semester(
                        resultSet.getLong("semester_id"),
                        resultSet.getString("semester_name"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date")
                );
                semesters.add(semester);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return semesters;
    }

    public Semester findById(int id) {
        Semester semester = null;
        String query = "SELECT * FROM semesters WHERE semester_id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                semester = new Semester(
                        resultSet.getLong("semester_id"),
                        resultSet.getString("semester_name"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return semester;
    }

    public Semester findByName(String name) {
        Semester semester = null;
        String query = "SELECT * FROM semesters WHERE semester_name = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                semester = new Semester(
                        resultSet.getLong("semester_id"),
                        resultSet.getString("semester_name"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return semester;
    }

    public boolean add(Semester semester) {
        String query = "INSERT INTO semesters (semester_name, start_date, end_date) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, semester.getSemesterName());
            statement.setDate(2, new Date(semester.getStartDate().getTime()));
            statement.setDate(3, new Date(semester.getEndDate().getTime()));
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

