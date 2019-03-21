package com.example.source;

import org.apache.ibatis.session.SqlSessionFactory;

public interface SqlSessionSource {
    SqlSessionFactory getSqlSessionFactory() throws Exception;
}
