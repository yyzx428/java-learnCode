package com.example.producer;

import com.alibaba.dubbo.config.annotation.Service;
import com.example.dubbo.api.Printer;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = Printer.class)
public class PeoplePrinterImp implements Printer {

    @Override
    public String sayHello(String name) {
        StringBuilder sb = new StringBuilder(100);
        return sb.append("（springBoot）我是人类！！").append(name).append(" 你好!!").toString();
    }
}
