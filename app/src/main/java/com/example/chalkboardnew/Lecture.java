package com.example.chalkboardnew;

public class Lecture {
    String lecture_name,lecture_date;

    public Lecture() {
    }

    public Lecture(String lecture_name, String lecture_date) {
        this.lecture_name = lecture_name;
        this.lecture_date = lecture_date;
    }

    public String getLecture_name() {
        return lecture_name;
    }

    public void setLecture_name(String lecture_name) {
        this.lecture_name = lecture_name;
    }

    public String getLecture_date() {
        return lecture_date;
    }

    public void setLecture_date(String lecture_date) {
        this.lecture_date = lecture_date;
    }
}
