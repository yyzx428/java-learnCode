package Algorithm;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class algorithm {
    public static ReentrantLock lock = new ReentrantLock();

    public static volatile int i = 0;

    public static void main(String[] args) {/*
        AtomicLong a = new AtomicLong(0);
        Project abc = new Project("11");
        AtomicLongFieldUpdater<Project> b = AtomicLongFieldUpdater.newUpdater(Project.class, "id");
        a.incrementAndGet();
        b.incrementAndGet(abc);*/
        /*ReflectionUtils.doWithLocalMethods(children.class, method -> {
            if (method.isBridge()) {
                Method method1= BridgeMethodResolver.findBridgedMethod(method);
                Stream.of(method.getReturnType()).forEach(System.out::println);
                Stream.of(method1.getReturnType()).forEach(System.out::println);
            }
        });*/
        List list = Arrays.asList(1, 1, 11, 1, 1);
        list.remove(1);
    }

    public void findDbDriver() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            System.out.println(driver.getClass().getName() + ":" + driver);
        }
    }
}
