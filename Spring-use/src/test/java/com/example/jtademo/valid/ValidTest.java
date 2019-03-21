package com.example.jtademo.valid;

import com.example.jtademo.JtademoApplication;
import com.example.jtademo.dto.ParameterDto;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JtademoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ValidTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void insert_test() {
        ParameterDto dto = new ParameterDto();
        dto.setEndTime(new Date(System.currentTimeMillis()));
        dto.setMoney(BigDecimal.ZERO);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity formEntity = new HttpEntity<>(objectToJson(dto), headers);
        Object result = restTemplate.postForObject("/insert", formEntity, Object.class);
        System.out.println(result);
    }

    @Test
    public void update_test() {
        ParameterDto dto = new ParameterDto();
        dto.setEndTime(new Date(System.currentTimeMillis()));
        dto.setStartTime(new Date(System.currentTimeMillis()));
        dto.setMoney(BigDecimal.ZERO);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity formEntity = new HttpEntity<>(objectToJson(dto), headers);
        Object result = restTemplate.postForObject("/update", formEntity, Object.class);
        System.out.println(result);
    }

    private String objectToJson(Object object) {
        StringWriter sw = new StringWriter();
        JsonGenerator gen = null;
        try {
            gen = new JsonFactory().createGenerator(sw);
            objectMapper.writeValue(gen, object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }
}
