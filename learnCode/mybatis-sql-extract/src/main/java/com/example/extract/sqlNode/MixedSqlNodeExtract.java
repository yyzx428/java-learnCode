package com.example.extract.sqlNode;

import com.example.factory.SqlExtractFactory;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;

public class MixedSqlNodeExtract extends AbstractSqlNodeExtract {
    public MixedSqlNodeExtract(SqlExtractFactory<SqlNode, String> factory) {
        super(factory);
    }

    @Override
    protected String getCustomSql(SqlNode source) throws Exception {
        Field field = MixedSqlNode.class.getDeclaredField("contents");
        ReflectionUtils.makeAccessible(field);
        List<SqlNode> sqlNodeList = (List<SqlNode>) ReflectionUtils.getField(field, source);
        StringBuilder resultSql = new StringBuilder();
        for (SqlNode node : sqlNodeList) {
            resultSql.append(factory.choose(node)).append(" ");
        }
        return resultSql.toString();
    }

    @Override
    public Class<?> getMatchClass() {
        return MixedSqlNode.class;
    }
}
