package com.example.extract.sqlSource;

import com.example.factory.SqlExtractFactory;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class RawSqlSourceExtract extends AbstractSqlSourceExtract {

    public RawSqlSourceExtract(SqlExtractFactory<SqlSource, String> factory) {
        super(factory);
    }

    @Override
    public Class<?> getMatchClass() {
        return RawSqlSource.class;
    }

    @Override
    public String getSql(SqlSource source) throws Exception {
        Field field = RawSqlSource.class.getDeclaredField("sqlSource");
        ReflectionUtils.makeAccessible(field);
        source = (SqlSource) ReflectionUtils.getField(field, source);
        return factory.choose(source);
    }
}
