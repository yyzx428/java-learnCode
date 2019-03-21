package com.example.gitMybatisSqlInfo.sqlNodeHandler;

import org.apache.ibatis.scripting.xmltags.ChooseSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;

public class ChooseSqlNodeImp extends AbstractSqlNode {

    public ChooseSqlNodeImp(SqlNodeFactory sqlNodeFactory) {
        super(sqlNodeFactory);
    }

    @Override
    public String getCustomSql(SqlNode sqlNode) throws Exception {
        Field defaultField = ChooseSqlNode.class.getDeclaredField("defaultSqlNode");
        Field ifField = ChooseSqlNode.class.getDeclaredField("ifSqlNodes");
        ReflectionUtils.makeAccessible(defaultField);
        ReflectionUtils.makeAccessible(ifField);
        SqlNode defaultSqlNode = (SqlNode) ReflectionUtils.getField(defaultField, sqlNode);
        List<SqlNode> sqlNodeList = (List<SqlNode>) ReflectionUtils.getField(ifField, sqlNode);
        StringBuilder resultSql = new StringBuilder();
        sqlNodeList.add(defaultSqlNode);

        for (SqlNode sqlNode1 : sqlNodeList) {
            resultSql.append(sqlNodeFactory.choose(sqlNode1)).append(" ");
        }
        return resultSql.toString();
    }
}
