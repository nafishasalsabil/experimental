package com.example.chalkboardnew;

public class Lecture {
    String lecture_name,lecture_date,student_id,student_name,status;

    public Lecture() {
    }

    public Lecture(String lecture_name, String lecture_date, String student_id, String student_name, String status) {
        this.lecture_name = lecture_name;
        this.lecture_date = lecture_date;
        this.student_id = student_id;
        this.student_name = student_name;
        this.status = status;
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

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
