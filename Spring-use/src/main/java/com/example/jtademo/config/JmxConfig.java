package com.example.jtademo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jmx.support.ConnectorServerFactoryBean;
import org.springframework.remoting.rmi.RmiRegistryFactoryBean;

@Configuration
public class JmxConfig {
    @Bean
    public RmiRegistryFactoryBean rmiRegistryFactoryBean(){
        RmiRegistryFactoryBean bean = new RmiRegistryFactoryBean();
        bean.setPort(1099);
        return bean;
    }

    @Bean
    @DependsOn({"rmiRegistryFactoryBean"})
    public ConnectorServerFactoryBean connectorServerFactoryBean()  {
        ConnectorServerFactoryBean bean = new ConnectorServerFactoryBean();
        bean.setServiceUrl("service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");
        return bean;
    }
}
