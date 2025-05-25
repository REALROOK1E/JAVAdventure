package com.tdde41;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/exam_sys";
    private static final String USER = "root";
    private static final String PASSWORD = "lzk100207";//edit here!!!!!!

    public static void initializeDatabase() {
        String[] createTables = {
                "CREATE TABLE IF NOT EXISTS users (" +
                        "user_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "username VARCHAR(35) UNIQUE NOT NULL, " +
                        "password VARCHAR(255) NOT NULL, " +
                        "role ENUM('teacher','student') NOT NULL)",

                "CREATE TABLE IF NOT EXISTS classrooms (" +
                        "classroom_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "classroom_name VARCHAR(35) UNIQUE NOT NULL)",

                "CREATE TABLE IF NOT EXISTS quizzes (" +
                        "quiz_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "quiz_name VARCHAR(100) NOT NULL, " +
                        "classroom_id INT, " +
                        "teacher_id INT, " +
                        "FOREIGN KEY (teacher_id) REFERENCES users(user_id), " +
                        "FOREIGN KEY (classroom_id) REFERENCES classrooms(classroom_id))",

                "CREATE TABLE IF NOT EXISTS questions (" +
                        "questions_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "description TEXT NOT NULL, " +
                        "answer TEXT NOT NULL, " +
                        "difficulty_level INT NOT NULL)",

                "CREATE TABLE IF NOT EXISTS quiz_questions (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "quiz_id INT NOT NULL, " +
                        "questions_id INT NOT NULL, " +
                        "FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id), " +
                        "FOREIGN KEY (questions_id) REFERENCES questions(questions_id))",

                "CREATE TABLE IF NOT EXISTS grades (" +
                        "grade_id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "student_id INT NOT NULL, " +
                        "quiz_id INT NOT NULL, " +
                        "score DECIMAL(5,2) NOT NULL, " +
                        "FOREIGN KEY (quiz_id) REFERENCES quizzes(quiz_id), " +
                        "FOREIGN KEY (student_id) REFERENCES users(user_id))"
        };

        try (Connection conn = getConnection()) {
            for (String sql : createTables) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute(sql);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }
    }
}