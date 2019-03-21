package com.example.gitMybatisSqlInfo.sqlNodeHandler;


import org.apache.ibatis.scripting.xmltags.SqlNode;

import java.util.Arrays;
import java.util.List;

public class WhereSqlNodeImp extends TrimSqlNodeImp {

    private static List<String> prefixList = Arrays.asList("AND ", "OR ", "AND\n", "OR\n", "AND\r", "OR\r", "AND\t", "OR\t");

    public WhereSqlNodeImp(SqlNodeFactory sqlNodeFactory) {
        super(sqlNodeFactory);
    }

    @Override
    public String getCustomSql(SqlNode sqlNode) throws Exception {
        String sql = super.getCustomSql(sqlNode);
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
}
