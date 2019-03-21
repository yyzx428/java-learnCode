package com.example.PrinterImp;

import interfaces.Printer;

public class SystemPrinterImp implements Printer {
    public void print() {
        System.out.println("控制台的日志实现者");
    }
}
