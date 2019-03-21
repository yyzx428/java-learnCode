package com.example.gitMybatisSqlInfo.sqlNodeHandler;

import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TrimSqlNode;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class TrimSqlNodeImp extends AbstractSqlNode {
    public TrimSqlNodeImp(SqlNodeFactory sqlNodeFactory) {
        super(sqlNodeFactory);
    }

    @Override
    public String getCustomSql(SqlNode sqlNode) throws Exception {
        Field field = TrimSqlNode.class.getDeclaredField("contents");
        ReflectionUtils.makeAccessible(field);
        SqlNode node = (SqlNode) ReflectionUtils.getField(field, sqlNode);
        return sqlNodeFactory.choose(node);
    }
}
