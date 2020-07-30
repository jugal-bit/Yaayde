package com.tanmaya.yaayde;

public class Student {
    private String id;
    private String name;
    private String institution;
    private String year;
    private String sem;
    private String roll;
    private String phone;

    public Student(){

    }

    public Student(String name, String sem, String roll) {
        this.name = name;
        this.sem= sem;
        this.roll = roll;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id=id;
    }


    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }


    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem= sem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
