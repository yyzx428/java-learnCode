package com.example.TypeHandlerTest;


import com.example.BaseDataTest;
import com.example.MybatisTest;
import com.example.SpringMybatis.SpringSqlSessionFactory;
import com.example.mybatisuse.dao.TemplateDao;
import com.example.mybatisuse.entity.Template;
import com.example.mybatisuse.queryDto.TemplateQueryDto;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MultimediaParamTest extends SpringSqlSessionFactory {
    @Before
    public void setUp() throws Exception {
        BaseDataTest.runScript(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(),
                "com/example/TypeHandlerTest/sql/CreateDB.sql");
    }

    @Test
    public void test(){
        try (SqlSession session =sqlSessionFactory.openSession()){
            TemplateDao dao=session.getMapper(TemplateDao.class);
            List<String> ids = new ArrayList<>();
            ids.add("1");
            ids.add("2");
            TemplateQueryDto queryDto =new TemplateQueryDto();
            queryDto.setStartTime(LocalDateTime.of(LocalDate.of(2018,8,19),LocalTime.MIN));
            queryDto.setEndTime(LocalDateTime.of(LocalDate.of(2018,8,20),LocalTime.MIN));
            List<Template> result=dao.getTemplateByIds(ids,queryDto);
            Assert.assertNotNull(result);
        }
    }
}
