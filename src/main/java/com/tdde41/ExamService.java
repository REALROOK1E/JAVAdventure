package com.tdde41;

import java.sql.*;
import java.util.*;

public class ExamService {
    // manage class
    public static boolean createClassroom(String name) {
        String sql = "INSERT INTO classrooms (classroom_name) VALUES (?)";
        return executeUpdate(sql, name);
    }

    public static List<String> listClassrooms() {
        return queryList("SELECT classroom_name FROM classrooms", "classroom_name");
    }

    // questions management,add
    public static boolean addQuestion(String desc, String answer, int difficulty) {
        String sql = "INSERT INTO questions (description, answer, difficulty_level) VALUES (?, ?, ?)";
        return executeUpdate(sql, desc, answer, difficulty);
    }

    public static boolean batchAddQuestions(List<String[]> questions) {
        String sql = "INSERT INTO questions (description, answer, difficulty_level) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (String[] q : questions) {
                stmt.setString(1, q[0]);
                stmt.setString(2, q[1]);
                stmt.setInt(3, Integer.parseInt(q[2]));
                stmt.addBatch();
            }

            int[] results = stmt.executeBatch();
            return Arrays.stream(results).allMatch(r -> r > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // quiz management
    public static boolean createQuiz(String name, int classroomId, int teacherId) {
        String sql = "INSERT INTO quizzes (quiz_name, classroom_id, teacher_id) VALUES (?, ?, ?)";
        return executeUpdate(sql, name, classroomId, teacherId);
    }

    // add questions
    public static boolean addQuestionToQuiz(int quizId, int questionId) {
        String sql = "INSERT INTO quiz_questions (quiz_id, questions_id) VALUES (?, ?)";
        return executeUpdate(sql, quizId, questionId);
    }

    // grade a student in a quiz
    public static boolean recordGrade(int studentId, int quizId, double score) {
        String sql = "INSERT INTO grades (student_id, quiz_id, score) VALUES (?, ?, ?)";
        return executeUpdate(sql, studentId, quizId, score);
    }

    // Encapsulates the common logic for operations
    private static boolean executeUpdate(String sql, Object... params) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static List<String> queryList(String sql, String column) {
        List<String> result = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                result.add(rs.getString(column));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}