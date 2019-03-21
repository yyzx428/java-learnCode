package com.example.SpringMybatis;


import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
import org.springframework.core.io.DefaultResourceLoader;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

public class DruidDataSourceFactory extends UnpooledDataSourceFactory {
    public DruidDataSourceFactory() {
        this.dataSource =new DruidDataSource();
    }

    public DataSource setProperties(String path) throws IOException {
        Properties properties = new Properties();
        properties.load(new DefaultResourceLoader().getResource(path).getInputStream());
        super.setProperties(properties);
        return dataSource;
    }
}
