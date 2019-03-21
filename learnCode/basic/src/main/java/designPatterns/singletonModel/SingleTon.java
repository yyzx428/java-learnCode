package designPatterns.singletonModel;

import java.util.concurrent.atomic.AtomicInteger;

public class SingleTon {
    private static SingleTon ourInstance = new SingleTon();

    public static SingleTon getInstance() {
        return ourInstance;
    }

    private volatile AtomicInteger a =new AtomicInteger(0);

    private SingleTon() {
    }

    public int getIncrement() {
        return a.getAndIncrement();
    }
}
