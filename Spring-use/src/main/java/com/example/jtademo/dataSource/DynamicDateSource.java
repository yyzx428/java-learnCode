package com.example.jtademo.dataSource;


import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class DynamicDateSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceHolder.getCurrentSource();
    }
}
