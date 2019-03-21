package com.example.jtademo.config.sharding;

import io.shardingsphere.core.rule.ShardingRule;
import io.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

public abstract class ShardingDataSourceFactoryBean implements FactoryBean<ShardingDataSource>, BeanFactoryAware {

    protected BeanFactory beanFactory;

    @Override
    public ShardingDataSource getObject() throws Exception {
        return new ShardingDataSource(createMap(), createShardingRule(), createConfigMap(),createProperties());
    }

    protected abstract Properties createProperties();

    protected abstract Map createConfigMap();

    protected abstract ShardingRule createShardingRule();

    protected abstract Map<String, DataSource> createMap();

    @Override
    public Class<?> getObjectType() {
        return ShardingDataSource.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
