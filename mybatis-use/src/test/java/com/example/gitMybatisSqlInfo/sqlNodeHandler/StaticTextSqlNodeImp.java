package com.example.gitMybatisSqlInfo.sqlNodeHandler;

import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class StaticTextSqlNodeImp extends AbstractSqlNode {

    public StaticTextSqlNodeImp(SqlNodeFactory sqlNodeFactory) {
        super(sqlNodeFactory);
    }

    @Override
    public String getCustomSql(SqlNode sqlNode) throws Exception {
        Field field = StaticTextSqlNode.class.getDeclaredField("text");
        ReflectionUtils.makeAccessible(field);
        String sql = (String) ReflectionUtils.getField(field, sqlNode);
        return sql;
    }
}
