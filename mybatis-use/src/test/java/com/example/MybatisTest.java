package com.example;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Reader;


public class MybatisTest {
    protected static SqlSessionFactory sqlSessionFactory;
    protected static String path = "mybatis-config.xml";
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected static String testEnvironment = "test";

    @BeforeClass
    public static void before() throws Exception{
        try (Reader reader = Resources.getResourceAsReader(path)) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader,testEnvironment);
        }
    }


}
