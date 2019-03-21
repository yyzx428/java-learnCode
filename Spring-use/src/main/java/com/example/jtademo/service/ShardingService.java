package com.example.jtademo.service;

import com.example.jtademo.dao.ShardingDao;
import com.example.jtademo.dataSource.DataSourceHolder;
import com.example.jtademo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: Sharding服务
 */
@Service
public class ShardingService {
    @Autowired
    private ShardingDao shardingDao;

    public List<Student> getStudentBySharding(){
        DataSourceHolder.putDateSource(DataSourceHolder.sharding);
        try {
            return shardingDao.getStudent();
        }finally {
            DataSourceHolder.removeDateSource(DataSourceHolder.sharding);
        }
    }
}
