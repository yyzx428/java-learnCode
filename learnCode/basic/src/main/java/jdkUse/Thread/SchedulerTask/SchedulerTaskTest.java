package jdkUse.Thread.SchedulerTask;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SchedulerTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(4,
                new ThreadFactoryBuilder()
                        .setNameFormat("DiscoveryClient-%d")
                        .setDaemon(true)
                        .build());
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                1,  // corePoolSize
                3,  // maxPoolSize
                0,  // keepAliveTime
                TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());  // use direct handoff
        ListeningExecutorService helper = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(1));  // use direct handoff
        Runnable runnable = () -> {
           System.out.println("完成");
        };
        TimedSupervisorTask supervisorTask = new TimedSupervisorTask(scheduled,executor,runnable,1000);
        helper.submit(supervisorTask).get();
    }


    static class TimedSupervisorTask extends TimerTask {

        private final ScheduledExecutorService scheduler;
        private final ThreadPoolExecutor executor;
        private AtomicInteger runCount;
        private final Runnable task;
        private final long timeoutMillis;
        private final AtomicLong delay;

        public TimedSupervisorTask(ScheduledExecutorService scheduler, ThreadPoolExecutor executor, Runnable task, long timeoutMillis) {
            this.scheduler = scheduler;
            this.executor = executor;
            this.task = task;
            this.timeoutMillis = timeoutMillis;
            this.delay = new AtomicLong(timeoutMillis);
            runCount = new AtomicInteger(0);
        }

        @Override
        public void run() {
            Future future = null;
            try {
                future = executor.submit(task);
                future.get(100000, TimeUnit.MILLISECONDS);  // block until done or timeout
            }catch (TimeoutException e){
                delay.compareAndSet(timeoutMillis,timeoutMillis*2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                runCount.incrementAndGet();
                if(future != null){
                    future.cancel(true);
                }

                if(!scheduler.isShutdown()){
                    scheduler.schedule(this,delay.get(),TimeUnit.MILLISECONDS);
                }
                System.out.println("执行"+runCount+"次");
            }
        }
    }
}
