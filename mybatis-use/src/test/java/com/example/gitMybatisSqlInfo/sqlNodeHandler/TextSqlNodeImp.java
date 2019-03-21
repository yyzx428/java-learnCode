package com.example.gitMybatisSqlInfo.sqlNodeHandler;

import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class TextSqlNodeImp extends AbstractSqlNode {

    private GenericTokenParser parser;

    public TextSqlNodeImp(SqlNodeFactory sqlNodeFactory) {
        super(sqlNodeFactory);
        parser = new GenericTokenParser("${", "}", content -> " ? ");
    }

    @Override
    public String getCustomSql(SqlNode sqlNode) throws Exception {
        Field field = TextSqlNode.class.getDeclaredField("text");
        ReflectionUtils.makeAccessible(field);
        String sql = (String) ReflectionUtils.getField(field, sqlNode);
        return parser.parse(sql);
    }
}
