package designPatterns.singletonModel;

public class TaskMain implements Runnable {
    private SingleTon singleTon;

    public TaskMain() {
        this.singleTon = SingleTon.getInstance();
    }

    @Override
    public void run() {
        int a = singleTon.getIncrement();
        System.out.println(Thread.currentThread().getName() + ":" + a);
    }
}
