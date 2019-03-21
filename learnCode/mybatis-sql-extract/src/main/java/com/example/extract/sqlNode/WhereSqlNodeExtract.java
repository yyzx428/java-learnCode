package com.example.extract.sqlNode;

import com.example.factory.SqlExtractFactory;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.WhereSqlNode;

import java.util.Arrays;
import java.util.List;

public class WhereSqlNodeExtract extends TrimSqlNodeExtract {
    private static List<String> prefixList = Arrays.asList("AND ", "OR ", "AND\n", "OR\n", "AND\r", "OR\r", "AND\t", "OR\t");

    public WhereSqlNodeExtract(SqlExtractFactory<SqlNode, String> factory) {
        super(factory);
    }

    @Override
    protected String getCustomSql(SqlNode source) throws Exception {
        String sql = super.getCustomSql(source);
        StringBuilder result = new StringBuilder(sql);
        if (prefixList != null) {
            for (String toRemove : prefixList) {
                if (sql.startsWith(toRemove)) {
                    result.delete(0, toRemove.trim().length());
                    break;
                }
            }
        }
        return "where " + result.toString();
    }

    @Override
    public Class<?> getMatchClass() {
        return WhereSqlNode.class;
    }
}
