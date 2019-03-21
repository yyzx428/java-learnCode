package com.example.gitMybatisSqlInfo.sqlNodeHandler;

import org.apache.ibatis.scripting.xmltags.SqlNode;

public interface SqlNodeImp {
    String getSql(SqlNode sqlNode) throws Exception;
}
