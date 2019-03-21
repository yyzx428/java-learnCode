package com.example.jtademo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.example.jtademo.config.sharding.CurrentShardingDataSourceFactoryBean;
import com.example.jtademo.dataSource.DataSourceHolder;
import com.example.jtademo.dataSource.DynamicDateSource;
import com.example.jtademo.transactionFactory.MultipleManagedTransactionFactory;
import io.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.handler.IgnoreTopLevelConverterNotFoundBindHandler;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

//@EnableTransactionManagement
public class DataSourceConfig implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor {

    private final static List<String> dataSourceKey = new LinkedList<>();

    static {
        dataSourceKey.add(DataSourceHolder.dev);
        dataSourceKey.add(DataSourceHolder.test);
        dataSourceKey.add(DataSourceHolder.sharding);
    }

    private ApplicationContext applicationContext;

    @Bean
    public CurrentShardingDataSourceFactoryBean currentShardingDataSourceFactoryBean() {
        return new CurrentShardingDataSourceFactoryBean();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Bean("dynamicDataSource")
    @DependsOn("1111")
    public AbstractRoutingDataSource initDataSource(ShardingDataSource currentShardingDataSourceFactoryBean) throws Exception {
        DynamicDateSource dynamicDataSource = new DynamicDateSource();

        Map<Object, Object> dataSources = new HashMap<>(4);

        dataSourceKey.forEach(key -> {
            DruidXADataSource dataSource = applicationContext.getBean(key, DruidXADataSource.class);
            AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
            xaDataSource.setXaDataSource(dataSource);
            xaDataSource.setUniqueResourceName(key);
            xaDataSource.setPoolSize(10);
            dataSources.put(key, xaDataSource);
        });

        dataSources.put(DataSourceHolder.sharding,currentShardingDataSourceFactoryBean);

        dynamicDataSource.setTargetDataSources(dataSources);

        return dynamicDataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(AbstractRoutingDataSource dynamicDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dynamicDataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath*:mapper*/**/*.xml"));
        sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource("classpath:mybatis-config.xml"));
        sessionFactory.setTransactionFactory(new MultipleManagedTransactionFactory());
        return sessionFactory.getObject();
    }


    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean(name = "userTransaction")
    public UserTransaction userTransaction() throws Throwable {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(10000);
        return userTransactionImp;
    }

    @Bean(name = "atomikosTransactionManager")
    public TransactionManager atomikosTransactionManager() throws Throwable {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        return userTransactionManager;
    }

    @Bean(name = "transactionManager")
    @DependsOn({"userTransaction", "atomikosTransactionManager"})
    public PlatformTransactionManager transactionManager() throws Throwable {
        JtaTransactionManager jta = new JtaTransactionManager(userTransaction(), atomikosTransactionManager());
        jta.setNestedTransactionAllowed(true);
        return jta;
    }

   /* @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(AbstractRoutingDataSource dynamicDataSource){
        return new DataSourceTransactionManager(dynamicDataSource);
    }*/

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        AtomicBoolean isPrimary = new AtomicBoolean(true);
        dataSourceKey.forEach(key -> {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DruidXADataSource.class);
            if (isPrimary.get()) {
                builder.getBeanDefinition().setPrimary(true);
                isPrimary.set(false);
            }
            registry.registerBeanDefinition(key, builder.getBeanDefinition());
        });
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }

    @Bean("1111")
    public String multipleDataSourceInitializer() {
        BindHandler bandHandler = new IgnoreTopLevelConverterNotFoundBindHandler();

        ConfigurableEnvironment environment = (ConfigurableEnvironment) applicationContext.getEnvironment();

        dataSourceKey.forEach(key -> {
            DruidDataSource dataSource = applicationContext.getBean(key, DruidDataSource.class);
            Binder.get(environment).bind(key, Bindable.ofInstance(dataSource), bandHandler);
        });

        return "11";
    }
}
