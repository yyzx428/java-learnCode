package com.example.jtademo;

import com.example.jtademo.entity.Student;
import com.example.jtademo.service.ShardingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JtademoApplication.class)
public class SharingJdbcTest {

    @Autowired
    ShardingService shardingService;

    @Test
    public void test() {
        List<Student> list = shardingService.getStudentBySharding();

        System.out.println(list);
    }
}
