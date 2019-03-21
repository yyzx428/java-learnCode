package com.example.mybatisuse.entity;

import java.util.List;

public class Student {
    private String id;
    private String name;

    private List<Subject> subjects;

    private Classmate classmate;

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

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Classmate getClassmate() {
        return classmate;
    }

    public void setClassmate(Classmate classmate) {
        this.classmate = classmate;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", subjects=" + subjects +
                ", classmate=" + classmate +
                '}';
    }
}
