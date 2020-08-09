package com.example.chalkboardnew;

public class user {
    public  String choice,courses;

    public  user()
    {

    }
    public user(String choice,String courses) {
        this.choice = choice;
        this.courses = courses;
    }
    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }



    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
}
