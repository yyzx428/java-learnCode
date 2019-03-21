package jdkUse.lock.CyclicBarrier;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

public class CyclicBarrierTest {
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(10,
            () -> System.out.println("10位选手已经准备好了，1 2 3 起跑！！！"));

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 0,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                new ThreadFactoryBuilder()
                        .setNameFormat("CyclicBarrier-Test-%d")
                        .build());

        for (int i = 0 ;i<=100 ;i++){
            executor.execute(new CyclicBarrierTask(i));
        }
    }

    static class CyclicBarrierTask implements Runnable{

        private int i;

        public CyclicBarrierTask(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                System.out.println(i+"号选手准备好了");
                cyclicBarrier.await();
            }catch (InterruptedException e){
                System.out.println(e);
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(i+"号选手起跑");
        }
    }
}
