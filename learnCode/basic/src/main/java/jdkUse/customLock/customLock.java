package jdkUse.customLock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class customLock {

    abstract class Sync extends AbstractQueuedSynchronizer {
        private static final long SerialVersionUID = 1L;

        abstract void lock();

        final boolean nonfairTryAcquire(int acquire) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (compareAndSetState(0, 1)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            } else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquire;
                if (nextc < 0)
                    throw new Error("MAX");
                setState(nextc);
                return true;
            }
            return false;
        }

        protected final boolean tryRelease(int release) {
            int c = getState() - release;
            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalStateException("asd");
            boolean free = false;
            if (c == 0) {
                free = true;
                setExclusiveOwnerThread(null);
            }
            setState(c);
            return free;
        }
    }
}
