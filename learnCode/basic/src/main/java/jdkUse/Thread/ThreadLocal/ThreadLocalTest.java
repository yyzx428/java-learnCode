package jdkUse.Thread.ThreadLocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalTest {
    public static ThreadLocal<Integer> resource = new ThreadLocal<>();

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        ThreadLocalTest.resource.set(10);
        for (; ; ) {
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + ":" + ThreadLocalTest.resource.get());
            });
            System.out.println(Thread.currentThread().getName() + ":" + ThreadLocalTest.resource.get());
        }
    }
}
