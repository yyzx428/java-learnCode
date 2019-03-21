package com.example.jtademo.testngSample;

import com.example.jtademo.JtademoApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@SpringBootTest(classes = JtademoApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class SampleTest extends AbstractTestNGSpringContextTests {

    @Test(threadPoolSize = 10,invocationCount = 100)
    public void test(){
        System.out.println(1);
    }
}
