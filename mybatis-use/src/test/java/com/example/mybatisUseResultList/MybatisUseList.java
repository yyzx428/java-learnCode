package com.example.mybatisUseResultList;


import com.example.BaseDataTest;
import com.example.MybatisTest;
import com.example.mybatisuse.dao.StudentDao;
import com.example.mybatisuse.entity.Classmate;
import com.example.mybatisuse.entity.Student;
import com.example.mybatisuse.entity.Subject;
import com.example.mybatisuse.queryDto.QueryDto;
import jdk.Exported;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.Reader;
import java.util.List;

public class MybatisUseList extends MybatisTest {


    @Before
    public void setUp() throws Exception {
        BaseDataTest.runScript(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(),
                "com/example/mybatisUseResultList/sql/CreateDB.sql");
    }

    @Test
    public void select_student_use_result_list() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            List<Student> list = session.getMapper(StudentDao.class).getAllStudentAndSubject();
            Assert.assertEquals(1, list.size());
            Student student = list.get(0);
            Assert.assertEquals("1", student.getId());
            Assert.assertEquals("张三", student.getName());
            Assert.assertEquals(1, student.getSubjects().size());
            Subject subject = student.getSubjects().get(0);
            Assert.assertEquals("语文", subject.getName());
            Assert.assertEquals(80, subject.getScore());
        }
    }

    @Test
    public void select_student() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            List<Student> list = session.getMapper(StudentDao.class).getAllStudent();
            Assert.assertEquals(1, list.size());
            Student student = list.get(0);
            Assert.assertEquals("1", student.getId());
            Assert.assertEquals("张三", student.getName());
            Assert.assertNull(student.getSubjects());
        }
    }

    @Test
    public void select_student_use_object() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            List<Student> list = session.getMapper(StudentDao.class).getAllStudentAndClassmate();
            Assert.assertEquals(1, list.size());
            Student student = list.get(0);
            Assert.assertEquals("1", student.getId());
            Assert.assertEquals("张三", student.getName());
            Assert.assertNotNull(student.getClassmate());
            Classmate classmate = student.getClassmate();
            Assert.assertEquals("三年一班", classmate.getName());
        }
    }

    @Test(expected = PersistenceException.class)
    public void select_student_use_ids_forEach() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            QueryDto dto = new QueryDto();
            dto.setId(1);
            dto.addStudentId("1");
            dto.addStudentId("2");
            List<Student> list = session.getMapper(StudentDao.class).getAllStudentByIds(dto);
        }
    }
}
