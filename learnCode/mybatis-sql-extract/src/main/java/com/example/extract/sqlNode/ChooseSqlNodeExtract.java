package com.example.extract.sqlNode;

import com.example.factory.SqlExtractFactory;
import org.apache.ibatis.scripting.xmltags.ChooseSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;

public class ChooseSqlNodeExtract extends AbstractSqlNodeExtract {
    public ChooseSqlNodeExtract(SqlExtractFactory<SqlNode, String> factory) {
        super(factory);
    }

    @Override
    protected String getCustomSql(SqlNode source) throws Exception {
        Field defaultField = ChooseSqlNode.class.getDeclaredField("defaultSqlNode");
        Field ifField = ChooseSqlNode.class.getDeclaredField("ifSqlNodes");
        ReflectionUtils.makeAccessible(defaultField);
        ReflectionUtils.makeAccessible(ifField);
        SqlNode defaultSqlNode = (SqlNode) ReflectionUtils.getField(defaultField, source);
        List<SqlNode> sqlNodeList = (List<SqlNode>) ReflectionUtils.getField(ifField, source);
        StringBuilder resultSql = new StringBuilder();
        sqlNodeList.add(defaultSqlNode);

        for (SqlNode sqlNode1 : sqlNodeList) {
            resultSql.append(factory.choose(sqlNode1)).append(" ");
        }
        return resultSql.toString();
    }

    @Override
    public Class<?> getMatchClass() {
        return ChooseSqlNode.class;
    }
}
