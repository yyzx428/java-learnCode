package com.example.gitMybatisSqlInfo.sqlNodeHandler;

import org.apache.ibatis.scripting.xmltags.IfSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class IfSqlNodeImp extends AbstractSqlNode {
    public IfSqlNodeImp(SqlNodeFactory sqlNodeFactory) {
        super(sqlNodeFactory);
    }

    @Override
    public String getCustomSql(SqlNode sqlNode) throws Exception {
        Field field = IfSqlNode.class.getDeclaredField("contents");
        ReflectionUtils.makeAccessible(field);
        SqlNode sqlNode1 = (SqlNode) ReflectionUtils.getField(field, sqlNode);
        return sqlNodeFactory.choose(sqlNode1);
    }
}
