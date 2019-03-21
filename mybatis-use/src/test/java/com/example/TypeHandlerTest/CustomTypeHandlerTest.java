package com.example.TypeHandlerTest;

import com.example.BaseDataTest;
import com.example.MybatisTest;
import com.example.mybatisuse.dao.TemplateDao;
import com.example.mybatisuse.entity.Content;
import com.example.mybatisuse.entity.Template;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CustomTypeHandlerTest extends MybatisTest {

    @Before
    public void setUp() throws Exception {
        BaseDataTest.runScript(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(),
                "com/example/TypeHandlerTest/sql/CreateDB.sql");
    }

    @Test
    public void custom_type_handler_test() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            //List<Template> list = session.getMapper(TemplateDao.class).getAllTemplate();
            List<Template> list = session.selectList("com.example.mybatisuse.dao.TemplateDao.getAllTemplate");
            Assert.assertEquals(1, list.size());
            Template template = list.get(0);
            Assert.assertEquals("1", template.getId());
            Assert.assertNotNull(template.getContent());
            Content content = template.getContent();
            Assert.assertEquals("标题", content.getTitle());
            Assert.assertEquals("内容", content.getContent());
        }
    }


    @Test
    public void insert_type_handler() {
        Template target = initTemplate();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            TemplateDao templateDao = session.getMapper(TemplateDao.class);
            int isSuccess = templateDao.insert(target);
            Assert.assertEquals(1, isSuccess);
            Assert.assertEquals(target,templateDao.getTemplate(target.getId()));
        }
    }

    private Template initTemplate() {
        Template template = new Template();
        template.setId("2");
        Content content = new Content();
        content.setTitle("标题");
        content.setContent("内容");
        template.setContent(content);
        return template;
    }
}
