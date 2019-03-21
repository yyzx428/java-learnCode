package com.example.jtademo.service;

import com.example.jtademo.entity.ClassMeta;
import com.example.jtademo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PeopleService {
    @Autowired
    ClassMetaService classMetaService;
    @Autowired
    StudentService studentService;


    @Transactional(rollbackFor = Exception.class)
    public void insert() throws Exception {
        Student student = new Student();
        student.setId("3");
        student.setName("小明");
        ClassMeta classMeta = new ClassMeta();
        classMeta.setId("3");
        classMeta.setName("三年一班");
        studentService.insert(student);
        classMetaService.insert(classMeta);
        student.setId("4");
        studentService.insert(student);
        throw new Exception();
    }

}
