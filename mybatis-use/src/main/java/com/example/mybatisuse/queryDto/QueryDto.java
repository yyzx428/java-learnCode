package com.example.mybatisuse.queryDto;

import java.util.LinkedList;
import java.util.List;

public class QueryDto {
    private List<String> studentId;

    private Integer id;

    public List<String> getStudentId() {
        return studentId;
    }

    public void setStudentId(List<String> studentId) {
        this.studentId = studentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void addStudentId(String id) {
        if (studentId == null) {
            studentId = new LinkedList<>();
        }
        studentId.add(id);
    }
}