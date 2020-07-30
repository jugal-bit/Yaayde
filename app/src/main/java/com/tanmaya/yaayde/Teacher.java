package com.tanmaya.yaayde;

public class Teacher {
    private String name;
    private String course_no;
    private String course_title;

    public Teacher(){

    }

    public Teacher(String course_title, String course_no, String name) {
        this.course_title = course_title;
        this.course_no = course_no;
        this.name = name;
    }



    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public String getCourse_no() {
        return course_no;
    }

    public void setCourse_no(String course_no) {
        this.course_no = course_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
