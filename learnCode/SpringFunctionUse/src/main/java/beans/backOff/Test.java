package beans.backOff;


import org.springframework.util.backoff.BackOffExecution;
import org.springframework.util.backoff.ExponentialBackOff;

public class Test {
    public static void main(String[] args) {
        ExponentialBackOff backOff = new ExponentialBackOff(1000L, 1.2);
        backOff.setMaxElapsedTime(10000L);
        BackOffExecution start = backOff.start();
        long waitTime;
        long totalTime = 0L;
        for (; ; ) {
            waitTime = start.nextBackOff();
            if (waitTime == BackOffExecution.STOP) {
                System.out.println("当前已使用的等待限制：" + totalTime);
                System.out.println("累计的等待时间超过了最大等待限制");
                break;
            }
            totalTime += waitTime;
            System.out.println("开始等待: " + waitTime);
            System.out.println("开始重试");
        }
    }
}
