package com.example.jtademo;

import com.example.jtademo.service.PeopleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JtademoApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JtademoApplicationTests {
    @Autowired
    PeopleService peopleService;

    @Test
    public void contextLoads() {
        while (true) {
            try {
                peopleService.insert();
            } catch (Exception e) {
            }
        }
    }

}
