package com.example.jtademo.dao;

import com.example.jtademo.entity.ClassMeta;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClassMetaDao {

    int insert(ClassMeta classMeta);
}
