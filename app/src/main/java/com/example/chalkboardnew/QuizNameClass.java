package com.example.chalkboardnew;

public class QuizNameClass {
    String quiz,quiz_date,quiz_marks;

    public QuizNameClass() {
    }

    public QuizNameClass(String quiz, String quiz_date, String quiz_marks) {
        this.quiz = quiz;
        this.quiz_date = quiz_date;
        this.quiz_marks = quiz_marks;
    }

    public String getQuiz() {
        return quiz;
    }

    public void setQuiz(String quiz) {
        this.quiz = quiz;
    }

    public String getQuiz_date() {
        return quiz_date;
    }

    public void setQuiz_date(String quiz_date) {
        this.quiz_date = quiz_date;
    }

    public String getQuiz_marks() {
        return quiz_marks;
    }

    public void setQuiz_marks(String quiz_marks) {
        this.quiz_marks = quiz_marks;
    }
}
