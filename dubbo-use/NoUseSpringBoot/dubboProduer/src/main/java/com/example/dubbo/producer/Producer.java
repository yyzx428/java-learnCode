package com.example.dubbo.producer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Producer {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/dubbo-demo-produer.xml");
        context.start();
        System.in.read();
    }
}
