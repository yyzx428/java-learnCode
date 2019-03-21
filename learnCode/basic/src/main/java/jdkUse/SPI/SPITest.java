package jdkUse.SPI;

import interfaces.Printer;

import java.util.ServiceLoader;

public class SPITest {
    public static void main(String[] args) {
        ServiceLoader<Printer> printers = ServiceLoader.load(Printer.class);
        for (Printer printer : printers) {
            printer.print();
        }
    }
}
