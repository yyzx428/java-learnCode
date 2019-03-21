package jdkUse.lock.StampedLock;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class StampedLockTest {
    private static StampedLock stampedLock = new StampedLock();
    private static ResourceFightArea resourceFightArea = new ResourceFightArea();

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 0,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                new ThreadFactoryBuilder()
                        .setNameFormat("CyclicBarrier-Test-%d")
                        .build());

        for (int i = 0; i <= 5; i++) {
            executor.execute(new StampedLockTask(i));
        }
    }

    static class StampedLockTask implements Runnable {

        private int i;

        public StampedLockTask(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            System.out.println(resourceFightArea.compareAndSet(i, i+1));
            System.out.println(i + ":" + resourceFightArea.getInt());
        }
    }


    static class ResourceFightArea {
        private volatile int i = 0;

        public int addAndGet() {
            long stamped = stampedLock.writeLock();
            try {
                return i++;
            } finally {
                stampedLock.unlockWrite(stamped);
            }
        }

        public int getAndAdd() {
            long stamped = stampedLock.writeLock();
            try {
                return ++i;
            } finally {
                stampedLock.unlockWrite(stamped);
            }
        }

        public boolean compareAndSet(int expert, int update) {
            long stamped = stampedLock.readLock();
            try {
                while (true) {
                    long ws = stampedLock.tryConvertToWriteLock(stamped);
                    if (ws != 0L) {
                        stamped = ws;
                        if (i == expert) {
                            i = update;
                            return true;
                        }
                        return false;
                    } else {
                        stampedLock.unlockRead(stamped);
                        stamped = stampedLock.writeLock();
                    }
                }
            } finally {
                stampedLock.unlock(stamped);
            }
        }

        public int getInt() {
            long stamped = stampedLock.tryOptimisticRead();
            if (!stampedLock.validate(stamped)) {
                stamped = stampedLock.readLock();
                try {
                    return i;
                } finally {
                    stampedLock.unlockRead(stamped);
                }
            }
            return i;
        }
    }
}
