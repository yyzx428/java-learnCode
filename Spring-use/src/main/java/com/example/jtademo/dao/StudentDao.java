package com.example.jtademo.dao;

import com.example.jtademo.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentDao {
    int insertObject(Student student);

    List<Student> queryList();
}
