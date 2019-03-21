package com.example.gitMybatisSqlInfo.sqlNodeHandler;

import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class ForEachSqlNodeImp extends AbstractSqlNode {

    public ForEachSqlNodeImp(SqlNodeFactory sqlNodeFactory) {
        super(sqlNodeFactory);
    }

    @Override
    public String getCustomSql(SqlNode sqlNode) throws Exception {
        Field field = ForEachSqlNode.class.getDeclaredField("contents");
        Field open = ForEachSqlNode.class.getDeclaredField("open");
        Field close = ForEachSqlNode.class.getDeclaredField("close");
        ReflectionUtils.makeAccessible(field);
        ReflectionUtils.makeAccessible(open);
        ReflectionUtils.makeAccessible(close);
        SqlNode node = (SqlNode) ReflectionUtils.getField(field, sqlNode);
        String openStr = (String) ReflectionUtils.getField(open, sqlNode);
        String closeStr = (String) ReflectionUtils.getField(close, sqlNode);
        return openStr + sqlNodeFactory.choose(node) + closeStr;
    }
}
