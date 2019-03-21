package com.example.consumer;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDubboConfiguration
public class ConsumerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ConsumerApplication.class, args);
        PrinterConsumer consumer = context.getBean(PrinterConsumer.class);

        while (true) {
            try {
                Thread.sleep(1000);
                System.out.println(consumer.sayHello("张三"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
