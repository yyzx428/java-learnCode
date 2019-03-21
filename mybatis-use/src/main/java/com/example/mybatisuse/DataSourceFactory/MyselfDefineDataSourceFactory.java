package com.example.mybatisuse.DataSourceFactory;


import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

public class MyselfDefineDataSourceFactory extends UnpooledDataSourceFactory {
    public MyselfDefineDataSourceFactory() {
        this.dataSource =new DruidDataSource();
    }
}
