package com.example.coursetrackingsystem.repositories;

import com.example.coursetrackingsystem.database.DatabaseConfig;
import com.example.coursetrackingsystem.models.Course;
import com.example.coursetrackingsystem.models.Lecturer;
import com.example.coursetrackingsystem.models.MarkSheet;
import com.example.coursetrackingsystem.models.Semester;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarkSheetRepository {

    public long getNextMarkSheetId() {
        String query = "SELECT MAX(marksheet_id) AS max_id FROM marksheets";
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

    public List<MarkSheet> findBySemester(Semester selectedSemester) {
        List<MarkSheet> markSheets = new ArrayList<>();
        String query = "SELECT * FROM marksheets WHERE semester_id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, selectedSemester.getSemesterId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long lecturerId = resultSet.getLong("lecturer_id");
                long courseId = resultSet.getLong("course_id");

                // Retrieve Lecturer details
                Lecturer lecturer = null;
                try (PreparedStatement lecStat = connection.prepareStatement("SELECT * FROM lecturers WHERE lecturer_id = ?")) {
                    lecStat.setLong(1, lecturerId);
                    ResultSet lecSet = lecStat.executeQuery();
                    if (lecSet.next()) {
                        lecturer = new Lecturer(
                                lecSet.getLong("lecturer_id"),
                                lecSet.getString("name"),
                                lecSet.getString("email")
                        );
                    }
                }

                // Retrieve Course details
                Course course = null;
                try (PreparedStatement courseStat = connection.prepareStatement("SELECT * FROM courses WHERE course_id = ?")) {
                    courseStat.setLong(1, courseId);
                    ResultSet courseSet = courseStat.executeQuery();
                    if (courseSet.next()) {
                        course = new Course(
                                courseSet.getLong("course_id"),
                                courseSet.getString("course_name"),
                                courseSet.getDate("start_date"),
                                lecturer,
                                selectedSemester
                        );
                    }
                }

                // Create MarkSheet object
                MarkSheet markSheet = new MarkSheet(
                        resultSet.getLong("marksheet_id"),
                        course,
                        lecturer,
                        selectedSemester,
                        resultSet.getString("submission_status"),
                        resultSet.getDate("estimated_completion_time"),
                        resultSet.getInt("total_students"),
                        resultSet.getInt("students_marked"),
                        resultSet.getFloat("average_score"),
                        resultSet.getFloat("highest_score"),
                        resultSet.getFloat("lowest_score")
                );
                markSheets.add(markSheet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return markSheets;
    }

    public long find(long semesterId, long courseId, long lecturerId) {
        String query = "SELECT marksheet_id FROM marksheets WHERE semester_id = ? AND course_id = ? AND lecturer_id = ?";
        long marksheetId = -1;

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, semesterId);
            statement.setLong(2, courseId);
            statement.setLong(3, lecturerId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                marksheetId = resultSet.getLong("marksheet_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return marksheetId;
    }

    public boolean update(long marksheetId, String submissionStatus, Date completionDate,
                          int totalStudents, int studentsMarked, float averageScore,
                          float highestScore, float lowestScore) {
        String query = "UPDATE marksheets SET submission_status = ?, estimated_completion_time = ?, total_students = ?, " +
                "students_marked = ?, average_score = ?, highest_score = ?, lowest_score = ? WHERE marksheet_id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, submissionStatus);
            statement.setDate(2, new Date(completionDate.getTime()));
            statement.setInt(3, totalStudents);
            statement.setInt(4, studentsMarked);
            statement.setFloat(5, averageScore);
            statement.setFloat(6, highestScore);
            statement.setFloat(7, lowestScore);
            statement.setLong(8, marksheetId);

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean add(MarkSheet markSheet) {
        String query = "INSERT INTO marksheets (marksheet_id, course_id, lecturer_id, semester_id, submission_status, estimated_completion_time, total_students, students_marked, average_score, highest_score, lowest_score) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, markSheet.getMarksheetId());
            statement.setLong(2, markSheet.getCourse().getCourseId());
            statement.setLong(3, markSheet.getLecturer().getLecturerId());
            statement.setLong(4, markSheet.getSemester().getSemesterId());
            statement.setString(5, markSheet.getSubmissionStatus());
            statement.setDate(6, new Date(markSheet.getEstimatedCompletionTime().getTime()));
            statement.setInt(7, markSheet.getTotalStudents());
            statement.setInt(8, markSheet.getStudentsMarked());
            statement.setFloat(9, markSheet.getAverageScore());
            statement.setFloat(10, markSheet.getHighestScore());
            statement.setFloat(11, markSheet.getLowestScore());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean drop(MarkSheet markSheet) {
        String query = "DELETE FROM marksheets WHERE marksheet_id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, markSheet.getMarksheetId());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
