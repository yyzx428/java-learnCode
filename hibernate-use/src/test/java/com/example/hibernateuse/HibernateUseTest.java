package com.example.hibernateuse;

import com.example.hibernateuse.entity.Student;
import org.hibernate.query.Query;
import org.junit.Test;

import java.util.List;


public class HibernateUseTest extends BaseHibernateTest {

    @Test
    public void save() {
        Student student = new Student();
        student.setId("222");
        student.setName("王五");
        session.save(student);
    }

    @Test
    public void select(){
        Query query = session.createQuery("from Student");
        List<Student> list = query.list();
        for(Student student :list){
            System.out.println(student);
        }
    }
}
