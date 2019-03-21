import java.util.concurrent.atomic.AtomicBoolean;

public class Outer {
    private static final AtomicBoolean monitor = new AtomicBoolean(true);
    String data = "外部类別";

    public static void main(String[] args) throws InterruptedException {
        Thread thread= new Thread(Outer::test);
        thread.start();

        while (!thread.isAlive()){
            System.out.println(thread.toString()+"线程还没启动");
        }
        for (int i = 0; i < 2; i++) {
            synchronized (monitor) {
               monitor.compareAndSet(false, true);
                System.out.println(i);
            }
        }
        thread.join(1000);
        System.out.println(7);
        synchronized (monitor) {
            System.out.println(10);
            monitor.notify();
        }
        System.out.println(8);
    }

    public static void test() {
        while (true) {
            try {
                synchronized (monitor) {
                    if (monitor.compareAndSet(true, false)) {
                        System.out.println(3);
                        monitor.wait();
                        System.out.println(4);
                    }
                }

                System.out.println(5);
            } catch (InterruptedException e) {
                System.out.println(6);
            }

        }
    }

    public class Inner {
        String data = "內部类別";

        public String getOuterData() {
            return Outer.this.data;
        }
    }
}
