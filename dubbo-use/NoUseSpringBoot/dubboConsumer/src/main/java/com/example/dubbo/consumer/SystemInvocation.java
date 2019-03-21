package com.example.dubbo.consumer;

import com.example.dubbo.api.Printer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SystemInvocation {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/dubbo-demo-consumer.xml");
        context.start();
        Printer printer = context.getBean("systemInvocation", Printer.class);
        while (true) {
            try {
                Thread.sleep(1000);
                String str = printer.sayHello("张三");
                System.out.println(str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
