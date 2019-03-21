package com.example.dubbo.producer;


import com.example.dubbo.api.Printer;

public class PeoplePrinterImp implements Printer {
    public String sayHello(String name) {
        StringBuilder sb = new StringBuilder(100);
        return sb.append("我是人类!! ").append(name).append(" 你好").toString();
    }
}
