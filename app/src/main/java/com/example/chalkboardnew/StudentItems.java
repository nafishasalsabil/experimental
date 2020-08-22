package com.example.chalkboardnew;

public class StudentItems {
     String id;
     String name;
     String status;
      String lecture_name;
     String lecture_date;


    public StudentItems() {
    }

    public StudentItems(String id, String name, String status, String lecture_name, String lecture_date) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.lecture_name = lecture_name;
        this.lecture_date = lecture_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    @Override
    public String toString() {
        return "StudentItems{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
