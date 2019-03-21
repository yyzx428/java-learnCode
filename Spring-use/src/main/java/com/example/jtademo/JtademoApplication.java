package com.example.jtademo;

import com.example.jtademo.config.DataSourceConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({DataSourceConfig.class})
@EnableMBeanExport
public class JtademoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JtademoApplication.class, args);
    }
}
