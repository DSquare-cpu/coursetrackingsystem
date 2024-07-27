package com.example.coursetrackingsystem.repositories;

import com.example.coursetrackingsystem.database.DatabaseConfig;
import com.example.coursetrackingsystem.models.Course;
import com.example.coursetrackingsystem.models.Lecturer;
import com.example.coursetrackingsystem.models.Semester;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository {


    public long getNextCourseId() {
        String query = "SELECT MAX(course_id) AS max_id FROM courses";
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

    public List<Course> findBySemester(Semester semester) {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM courses WHERE semester_id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, semester.getSemesterId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("lecturer_id");
                PreparedStatement lec_stat = connection.prepareStatement("SELECT * FROM lecturers WHERE lecturer_id = ?");
                lec_stat.setLong(1, id);
                ResultSet lec_set = lec_stat.executeQuery();

                Lecturer lecturer = null;
                if (lec_set.next()){
                    lecturer = new Lecturer(
                            lec_set.getLong("lecturer_id"),
                            lec_set.getString("name"),
                            lec_set.getString("email")
                    );
                }
                Course course = new Course(
                        resultSet.getLong("course_id"),
                        resultSet.getString("course_name"),
                        resultSet.getDate("start_date"),
                        lecturer,
                        semester
                );
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public List<Course> findByLecturer(Lecturer lecturer) {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM courses WHERE lecturer_id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, lecturer.getLecturerId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long sem_id = resultSet.getLong("semester_id");
                PreparedStatement sem_stat = connection.prepareStatement("SELECT * FROM semesters WHERE semester_id = ?");
                sem_stat.setLong(1, sem_id);
                ResultSet sem_set = sem_stat.executeQuery();

                Semester semester = null;
                if (sem_set.next()){
                    semester = new Semester(
                            sem_set.getLong("semester_id"),
                            sem_set.getString("semester_name"),
                            sem_set.getDate("start_date"),
                            sem_set.getDate("end_date")
                    );
                }
                Course course = new Course(
                        resultSet.getLong("course_id"),
                        resultSet.getString("course_name"),
                        resultSet.getDate("start_date"),
                        lecturer,
                        semester
                );
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public void save(Course course) {
        String query = "INSERT INTO courses (course_id, course_name, lecturer_id, semester_id, start_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, course.getCourseId());
            statement.setString(2, course.getCourseName());
            statement.setLong(3, course.getLecturer().getLecturerId());
            statement.setLong(4, course.getSemester().getSemesterId());
            statement.setDate(5, Date.valueOf(((Date)course.getStartDate()).toLocalDate()));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Course findByName(Semester semester, String courseName) {
        Course course = null;
        String query = "SELECT * FROM courses WHERE semester_id = ? AND course_name = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, semester.getSemesterId());
            statement.setString(2, courseName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                long id = resultSet.getLong("lecturer_id");
                PreparedStatement lec_stat = connection.prepareStatement("SELECT * FROM lecturers WHERE lecturer_id = ?");
                lec_stat.setLong(1, id);
                ResultSet lec_set = lec_stat.executeQuery();

                Lecturer lecturer = null;
                if (lec_set.next()){
                    lecturer = new Lecturer(
                            lec_set.getLong("lecturer_id"),
                            lec_set.getString("name"),
                            lec_set.getString("email")
                    );
                }
                course = new Course(
                        resultSet.getLong("course_id"),
                        resultSet.getString("course_name"),
                        resultSet.getDate("start_date"),
                        lecturer,
                        semester
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return course;
    }

    public void drop(Semester semester, Course course) {
        String query = "DELETE FROM courses WHERE semester_id = ? AND course_id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, semester.getSemesterId());
            statement.setLong(2, course.getCourseId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
