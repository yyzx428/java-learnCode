package com.example.gitMybatisSqlInfo.sqlNodeHandler;

import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;

public class MixedSqlNodeImp extends AbstractSqlNode {
    public MixedSqlNodeImp(SqlNodeFactory sqlNodeFactory) {
        super(sqlNodeFactory);
    }

    @Override
    public String getCustomSql(SqlNode sqlNode) throws Exception {
        Field field = MixedSqlNode.class.getDeclaredField("contents");
        ReflectionUtils.makeAccessible(field);
        List<SqlNode> sqlNodeList = (List<SqlNode>) ReflectionUtils.getField(field, sqlNode);
        StringBuilder resultSql = new StringBuilder();
        for (SqlNode node : sqlNodeList) {
            resultSql.append(sqlNodeFactory.choose(node)).append(" ");
        }
        return resultSql.toString();
    }
}
