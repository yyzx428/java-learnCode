package com.example.SpringMybatis;


import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.BeforeClass;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;


public class SpringSqlSessionFactory {
    public static SqlSessionFactory sqlSessionFactory;
    private static String mapperLocation = "classpath*:mapper*/**/*.xml";
    private static String configLocation = "classpath:com/example/SpringMybatis/mybatis-config.xml";
    private static String applicationConfigLocation = "com/example/SpringMybatis/application.properties";

    @BeforeClass
    public static void before() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver pathResolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(pathResolver.getResources(mapperLocation));
        sqlSessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
        sqlSessionFactoryBean.setDataSource(new DruidDataSourceFactory().setProperties(applicationConfigLocation));
        sqlSessionFactory = sqlSessionFactoryBean.getObject();
    }
}
