package com.example.extract.sqlSource;

import com.example.factory.SqlExtractFactory;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.SqlSource;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class StaticSqlSourceExtract extends AbstractSqlSourceExtract {

    public StaticSqlSourceExtract(SqlExtractFactory<SqlSource, String> factory) {
        super(factory);
    }

    @Override
    public Class<?> getMatchClass() {
        return StaticSqlSource.class;
    }

    @Override
    public String getSql(SqlSource source) throws Exception {
        Field field = StaticSqlSource.class.getDeclaredField("sql");
        ReflectionUtils.makeAccessible(field);
        return (String) ReflectionUtils.getField(field, source);
    }
}
