package com.example.extract.sqlNode;

import com.example.factory.SqlExtractFactory;
import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public class TextSqlNodeExtract extends AbstractSqlNodeExtract {
    private GenericTokenParser parser;

    public TextSqlNodeExtract(SqlExtractFactory<SqlNode, String> factory) {
        super(factory);
        parser = new GenericTokenParser("${", "}", content -> content);
    }

    @Override
    protected String getCustomSql(SqlNode source) throws Exception {
        Field field = TextSqlNode.class.getDeclaredField("text");
        ReflectionUtils.makeAccessible(field);
        String sql = (String) ReflectionUtils.getField(field, source);
        return parser.parse(sql);
    }

    @Override
    public Class<?> getMatchClass() {
        return TextSqlNode.class;
    }
}
