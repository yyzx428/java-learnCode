package jdkUse.lock.CountDownLatch;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {
    private static CountDownLatch countDownLatch = new CountDownLatch(10);
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 0,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                new ThreadFactoryBuilder()
                        .setNameFormat("CountDownLatch-Test-%d")
                        .build());

        for (int i = 0 ;i< 10 ;i++){
            executor.execute(new CountDownLatchTask(i));
        }

        countDownLatch.await();
        System.out.println("10位工人完成任务");
    }

    static class CountDownLatchTask implements Runnable{
        private int i;

        public CountDownLatchTask(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000*new Random().nextInt(100));
                System.out.println(i+"号工人完成任务");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();
            }
        }
    }
}
