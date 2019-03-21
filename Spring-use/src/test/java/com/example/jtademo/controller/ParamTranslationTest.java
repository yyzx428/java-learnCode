package com.example.jtademo.controller;


import com.beust.jcommander.internal.Lists;
import com.example.jtademo.JtademoApplication;
import com.example.jtademo.dto.ParamTranslationDTO;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = JtademoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParamTranslationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testUrlEncoderParam()  {
        ParamTranslationDTO paramTranslationDTO = new ParamTranslationDTO();
        String name = "张三";
        String name_encode = URLEncoder.encode(name);
        paramTranslationDTO.setName(name_encode);
        String json = object2Json(paramTranslationDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON_UTF8));
        HttpEntity<String> httpEntity = new HttpEntity<>(json, headers);
        System.out.println("请求前:" + name_encode);
        String s = testRestTemplate.postForObject("/test/decoderName", httpEntity, String.class);
        System.out.println("返回结果:" + s);
        Assert.assertEquals(name, s);
    }

    private String object2Json(Object entity) {
        StringWriter sw = new StringWriter();
        try {
            objectMapper.writeValue(sw, entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }
}
