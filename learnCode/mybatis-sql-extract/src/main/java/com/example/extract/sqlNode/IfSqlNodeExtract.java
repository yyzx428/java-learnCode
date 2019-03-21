package com.example.extract.sqlNode;

import com.example.factory.SqlExtractFactory;
import org.apache.ibatis.scripting.xmltags.IfSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class IfSqlNodeExtract extends AbstractSqlNodeExtract {
    public IfSqlNodeExtract(SqlExtractFactory<SqlNode, String> factory) {
        super(factory);
    }

    @Override
    protected String getCustomSql(SqlNode source) throws Exception {
        Field field = IfSqlNode.class.getDeclaredField("contents");
        ReflectionUtils.makeAccessible(field);
        SqlNode sqlNode1 = (SqlNode) ReflectionUtils.getField(field, source);
        return factory.choose(sqlNode1);
    }

    @Override
    public Class<?> getMatchClass() {
        return IfSqlNode.class;
    }
}
