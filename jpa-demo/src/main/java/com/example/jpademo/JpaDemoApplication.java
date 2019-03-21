package com.example.jpademo;

import com.example.jpademo.dao.StudentDAO;
import com.example.jpademo.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@SpringBootApplication
@EnableJpaRepositories
@Slf4j
public class JpaDemoApplication implements ApplicationRunner {

    @Autowired
    private StudentDAO studentDAO;

    public static void main(String[] args) {
        SpringApplication.run(JpaDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Student> student = studentDAO.findAll();
        log.info("更新前:{}", student);
//        student.setName("李四");
//        student.setUpdateUser("张三");
//        studentDAO.saveAndFlush(student);
//        student = studentDAO.findById(student.getId()).get();
//        log.info("更新后:{}", student);
    }
}
