package com.example.mybatisuse.dao;

import com.example.mybatisuse.entity.Student;
import com.example.mybatisuse.queryDto.QueryDto;

import java.util.List;

public interface StudentDao {
    List<Student> getAllStudent();

    List<Student> getAllStudentAndSubject();

    List<Student> getAllStudentAndClassmate();

    List<Student> getAllStudentByIds(QueryDto dto);
}
