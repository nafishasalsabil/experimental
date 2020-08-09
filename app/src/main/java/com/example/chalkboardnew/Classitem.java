package com.example.chalkboardnew;

public class Classitem {

    String coursetitle;
    String courseno;

    public Classitem(String classname,String course) {
        this.coursetitle = classname;
        this.courseno = course;
    }

    public String getCoursetitle() {
        return coursetitle;
    }

    public void setCoursetitle(String classname) {
        this.coursetitle = classname;
    }

    public String getCourseno() {
        return courseno;
    }

    public void setCourseno(String course) {
        this.courseno = course;
    }
}
