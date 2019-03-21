package com.example.producer;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
@EnableDubboConfiguration
public class ProducerApplication {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(ProducerApplication.class, args);
        System.in.read();
    }
}
