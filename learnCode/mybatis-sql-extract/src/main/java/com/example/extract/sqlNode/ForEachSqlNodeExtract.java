package com.example.extract.sqlNode;

import com.example.factory.SqlExtractFactory;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class ForEachSqlNodeExtract extends AbstractSqlNodeExtract {

    public ForEachSqlNodeExtract(SqlExtractFactory<SqlNode, String> factory) {
        super(factory);
    }

    @Override
    protected String getCustomSql(SqlNode source) throws Exception {
        Field field = ForEachSqlNode.class.getDeclaredField("contents");
        Field open = ForEachSqlNode.class.getDeclaredField("open");
        Field close = ForEachSqlNode.class.getDeclaredField("close");
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.makeAccessible(open);
        ReflectionUtils.makeAccessible(close);
        SqlNode node = (SqlNode) ReflectionUtils.getField(field, source);
        String openStr = (String) ReflectionUtils.getField(open, source);
        String closeStr = (String) ReflectionUtils.getField(close, source);
        return openStr + factory.choose(node) + closeStr;
    }

    @Override
    public Class<?> getMatchClass() {
        return ForEachSqlNode.class;
    }
}
