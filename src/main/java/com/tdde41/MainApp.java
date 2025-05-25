package com.tdde41;


import java.util.Arrays;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        //init connection
        DatabaseManager.initializeDatabase();
        // create user
        AuthService.createUser("teacher1", "pass123", "teacher");
        AuthService.createUser("student1", "pass123", "student");
        // varify
        int teacherId = AuthService.authenticate("teacher1", "pass123");
        if (teacherId != -1) {
            System.out.println( AuthService.getUserRole(teacherId));//test
        }
        // new classroom
        ExamService.createClassroom("TDDE41_ROOM1");
        //add question
        ExamService.addQuestion("2+2=?", "4", 1);
        // batch adding question
        List<String[]> batchQuestions = Arrays.asList(
            new String[]{"3*3=?", "9", "1"},
            new String[]{"Capital of Sweden?", "Stockholm", "2"}
        );
        ExamService.batchAddQuestions(batchQuestions);
        // create new quiz
        ExamService.createQuiz("TDDE41_exam", 1, teacherId);

        //add question to quiz,kindly see the implement,very simple
        ExamService.addQuestionToQuiz(1, 1);
        ExamService.addQuestionToQuiz(1, 2);

        // record the grade
        int studentId = AuthService.authenticate("student1", "pass123");
        ExamService.recordGrade(studentId, 1, 85.5);

    }
}