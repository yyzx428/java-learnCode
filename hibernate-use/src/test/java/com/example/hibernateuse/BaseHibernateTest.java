package com.example.hibernateuse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;


public class BaseHibernateTest {

    protected SessionFactory sessionFactory;
    protected Session session;
    protected Transaction transaction;

    @Before
    public void setUp(){
        //创建对象
        Configuration configuration = new Configuration().configure();
        //创建服务对象
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        //创建会话工厂
        sessionFactory = configuration.buildSessionFactory();
        //创建会话对象
        session = sessionFactory.openSession();
        //开启事物
        transaction = session.beginTransaction();
    }

    @After
    public void stop(){
        transaction.commit();
        session.close();
        sessionFactory.close();
    }
}
