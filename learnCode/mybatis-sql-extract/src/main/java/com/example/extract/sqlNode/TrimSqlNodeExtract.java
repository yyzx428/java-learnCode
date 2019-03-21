package com.example.extract.sqlNode;

import com.example.factory.SqlExtractFactory;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TrimSqlNode;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class TrimSqlNodeExtract extends AbstractSqlNodeExtract {
    public TrimSqlNodeExtract(SqlExtractFactory<SqlNode, String> factory) {
        super(factory);
    }

    @Override
    protected String getCustomSql(SqlNode source) throws Exception {
        Field field = TrimSqlNode.class.getDeclaredField("contents");
        ReflectionUtils.makeAccessible(field);
        SqlNode node = (SqlNode) ReflectionUtils.getField(field, source);
        return factory.choose(node);
    }

    @Override
    public Class<?> getMatchClass() {
        return TrimSqlNode.class;
    }
}
