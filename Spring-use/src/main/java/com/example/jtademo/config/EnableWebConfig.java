package com.example.jtademo.config;

import com.beust.jcommander.internal.Lists;
import com.example.jtademo.requestBodyAdvice.URLDecoderRequestBodyAdvice;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
public class EnableWebConfig extends WebMvcAutoConfiguration.EnableWebMvcConfiguration {
    public EnableWebConfig(ObjectProvider<WebMvcProperties> mvcPropertiesProvider, ObjectProvider<WebMvcRegistrations> mvcRegistrationsProvider, ListableBeanFactory beanFactory) {
        super(mvcPropertiesProvider, mvcRegistrationsProvider, beanFactory);
    }

    @Bean
    @Override
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter requestMappingHandlerAdapter = super.requestMappingHandlerAdapter();
        requestMappingHandlerAdapter.setRequestBodyAdvice(Lists.newArrayList(new URLDecoderRequestBodyAdvice()));
        return requestMappingHandlerAdapter;
    }
}
