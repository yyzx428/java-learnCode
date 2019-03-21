package com.example.jtademo.dataSource;

public class DataSourceHolder {
    public static final String dev = "ds.db.config.dev";
    public static final String test = "ds.db.config.test";
    public static final String sharding = "ds.db.config.sharding";
    public static final String dev_short = "dev";
    public static final String test_short = "test";

    private static final ThreadLocal<String> currentDateSource = new ThreadLocal<>();


    public static String getCurrentSource() {
        return currentDateSource.get();
    }

    public static void putDateSource(String id) {
        currentDateSource.set(id);
    }

    public static void removeDateSource(String id) {
        currentDateSource.remove();
    }

}
