package jdkUse.threadPool.scheduled;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

        //等待任务完成后再延迟1秒
        executor.scheduleWithFixedDelay(() -> {
            System.out.println(Thread.currentThread()+":"+LocalDateTime.now());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },0L,1L,TimeUnit.SECONDS);

        //已1秒为间隔不管任务是否完成
        executor.scheduleAtFixedRate(() -> {
            System.out.println(Thread.currentThread()+":"+LocalDateTime.now());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },0L,1L,TimeUnit.SECONDS);
    }
}
