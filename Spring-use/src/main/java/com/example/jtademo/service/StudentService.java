package com.example.jtademo.service;

import com.example.jtademo.dao.StudentDao;
import com.example.jtademo.dataSource.DataSourceHolder;
import com.example.jtademo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentDao studentDao;

    public int insert(Student student){
        DataSourceHolder.putDateSource(DataSourceHolder.dev);
        try {
            return studentDao.insertObject(student);
        }finally {
            DataSourceHolder.removeDateSource(DataSourceHolder.dev);
        }
    }

    public List<Student> queryList() {
        DataSourceHolder.putDateSource(DataSourceHolder.dev);
        try {
            return studentDao.queryList();
        }finally {
            DataSourceHolder.removeDateSource(DataSourceHolder.dev);
        }
    }
}
