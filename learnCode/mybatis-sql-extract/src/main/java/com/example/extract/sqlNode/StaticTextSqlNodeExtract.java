package com.example.extract.sqlNode;

import com.example.factory.SqlExtractFactory;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class StaticTextSqlNodeExtract extends AbstractSqlNodeExtract {
    public StaticTextSqlNodeExtract(SqlExtractFactory<SqlNode, String> factory) {
        super(factory);
    }

    @Override
    protected String getCustomSql(SqlNode source) throws Exception {
        Field field = StaticTextSqlNode.class.getDeclaredField("text");
        ReflectionUtils.makeAccessible(field);
        return (String) ReflectionUtils.getField(field, source);
    }

    @Override
    public Class<?> getMatchClass() {
        return StaticTextSqlNode.class;
    }
}
