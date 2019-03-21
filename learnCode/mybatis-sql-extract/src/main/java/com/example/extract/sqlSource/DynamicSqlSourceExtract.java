package com.example.extract.sqlSource;

import com.example.extract.sqlNode.factory.SqlNodeExtractFactoryImp;
import com.example.factory.SqlExtractFactory;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;

public class DynamicSqlSourceExtract extends AbstractSqlSourceExtract {

    private SqlExtractFactory<SqlNode, String> sqlNodeFactory = new SqlNodeExtractFactoryImp();

    public DynamicSqlSourceExtract(SqlExtractFactory<SqlSource, String> factory) {
        super(factory);
    }

    @Override
    public Class<?> getMatchClass() {
        return DynamicSqlSource.class;
    }

    @Override
    public String getSql(SqlSource source) throws Exception {
        Field field = DynamicSqlSource.class.getDeclaredField("rootSqlNode");
        ReflectionUtils.makeAccessible(field);
        MixedSqlNode mixedSqlNode = (MixedSqlNode) ReflectionUtils.getField(field, source);
        Field field1 = MixedSqlNode.class.getDeclaredField("contents");
        ReflectionUtils.makeAccessible(field1);
        List<SqlNode> list = (List<SqlNode>) ReflectionUtils.getField(field1, mixedSqlNode);
        StringBuilder st = new StringBuilder();
        for (SqlNode sqlNode : list) {
            try {
                st.append(sqlNodeFactory.choose(sqlNode).trim()).append(" ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return st.toString();
    }
}
