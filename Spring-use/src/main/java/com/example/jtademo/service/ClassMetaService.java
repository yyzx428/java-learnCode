package com.example.jtademo.service;

import com.example.jtademo.dao.ClassMetaDao;
import com.example.jtademo.dataSource.DataSourceHolder;
import com.example.jtademo.entity.ClassMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassMetaService {
    @Autowired
    ClassMetaDao classMetaDao;

    public int insert(ClassMeta classMeta) throws Exception {
        DataSourceHolder.putDateSource(DataSourceHolder.test);
        try {
            classMetaDao.insert(classMeta);
            return 1;
        } catch (Exception e) {
            throw e;
        } finally {
            DataSourceHolder.removeDateSource(DataSourceHolder.test);
        }
    }
}
