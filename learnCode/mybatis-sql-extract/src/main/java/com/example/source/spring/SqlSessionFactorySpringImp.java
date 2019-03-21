package com.example.source.spring;

import com.example.source.SqlSessionSource;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class SqlSessionFactorySpringImp implements SqlSessionSource {

    @Override
    public SqlSessionFactory getSqlSessionFactory() throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(new PooledDataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath*:mapper*/**/*.xml"));
        sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource("classpath:mybatis-config.xml"));
        return sessionFactory.getObject();
    }
}
