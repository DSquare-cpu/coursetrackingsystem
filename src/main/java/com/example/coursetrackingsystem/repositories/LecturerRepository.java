package com.example.coursetrackingsystem.repositories;

import com.example.coursetrackingsystem.database.DatabaseConfig;
import com.example.coursetrackingsystem.models.Lecturer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LecturerRepository {

    public long getNextLecturerId() {
        String query = "SELECT MAX(lecturer_id) AS max_id FROM lecturers";
        long nextId = 1; // Default to 1 if no rows in the table

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                nextId = resultSet.getLong("max_id") + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nextId;
    }

    public List<Lecturer> findAll() {
        List<Lecturer> lecturers = new ArrayList<>();
        String query = "SELECT * FROM lecturers";

        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Lecturer lecturer = new Lecturer(
                        resultSet.getLong("lecturer_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                );
                lecturers.add(lecturer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lecturers;
    }

    public Lecturer findById(long id) {
        Lecturer lecturer = null;
        String query = "SELECT * FROM lecturers WHERE lecturer_id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                lecturer = new Lecturer(
                        resultSet.getLong("lecturer_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lecturer;
    }

    public Lecturer findByName(String name) {
        Lecturer lecturer = null;
        String query = "SELECT * FROM lecturers WHERE name = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                lecturer = new Lecturer(
                        resultSet.getLong("lecturer_id"),
                        resultSet.getString("name"),
                        resultSet.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lecturer;
    }

    public boolean addLecturer(Lecturer lecturer){
        String query = "INSERT INTO lecturers (lecturer_id, name, email) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, lecturer.getLecturerId());
            statement.setString(2, lecturer.getName());
            statement.setString(3, lecturer.getEmail());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
