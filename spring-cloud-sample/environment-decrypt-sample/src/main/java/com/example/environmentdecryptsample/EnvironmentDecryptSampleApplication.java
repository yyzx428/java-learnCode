package com.example.environmentdecryptsample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EnvironmentDecryptSampleApplication implements ApplicationRunner {

    @Value("${foo}")
    private String foo;

    @Value("${foo1}")
    private String foo1;

    public static void main(String[] args) {
        SpringApplication.run(EnvironmentDecryptSampleApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(foo);
        System.out.println(foo1);
    }
}
