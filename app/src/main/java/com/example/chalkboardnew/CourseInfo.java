package com.example.chalkboardnew;

import java.util.ArrayList;

public class CourseInfo {
    private String courseTitle,courseNo,semester,credits,courseType,noOfQuizes;

    public CourseInfo() {
    }

    public CourseInfo(String courseTitle, String courseNo, String semester, String credits, String courseType, String noOfQuizes) {
        this.courseTitle = courseTitle;
        this.courseNo = courseNo;
        this.semester = semester;
        this.credits = credits;
        this.courseType = courseType;
        this.noOfQuizes = noOfQuizes;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getNoOfQuizes() {
        return noOfQuizes;
    }

    public void setNoOfQuizes(String noOfQuizes) {
        this.noOfQuizes = noOfQuizes;
    }

    @Override
    public String toString() {
        return  this.getCourseTitle();
    }

}
