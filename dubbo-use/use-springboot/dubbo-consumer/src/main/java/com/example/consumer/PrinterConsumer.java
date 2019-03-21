package com.example.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.dubbo.api.Printer;
import org.springframework.stereotype.Component;

@Component
public class PrinterConsumer {

    @Reference
    private Printer printer;

    public String sayHello(String name) {
        return printer.sayHello(name);
    }
}
