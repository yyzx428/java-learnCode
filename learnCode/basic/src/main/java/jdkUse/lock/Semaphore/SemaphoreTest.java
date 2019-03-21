package jdkUse.lock.Semaphore;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class SemaphoreTest {
    private static Semaphore semaphore = new Semaphore(10);

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 0,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                new ThreadFactoryBuilder()
                        .setNameFormat("Semaphore-Test-%d")
                        .setUncaughtExceptionHandler(new CustomUncaughtExceptionHandler())
                        .build(),
                new CustomRejectedExecutionHandler());

        for (int i = 0 ;i<=100 ;i++){
            executor.execute(new SemaphoreTask(i));
        }
    }

    static class SemaphoreTask implements Runnable {
        private int i;

        public SemaphoreTask(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("第"+i+"位正在买票");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                semaphore.release();
            }
        }
    }

    static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("任务拒绝");
        }
    }

    static class CustomUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("线程未捕获异常");
        }
    }

}
