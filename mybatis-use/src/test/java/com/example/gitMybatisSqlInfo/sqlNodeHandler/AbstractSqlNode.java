package com.example.gitMybatisSqlInfo.sqlNodeHandler;


import org.apache.ibatis.scripting.xmltags.SqlNode;

public abstract class AbstractSqlNode implements SqlNodeImp {
    protected SqlNodeFactory sqlNodeFactory;

    public AbstractSqlNode(SqlNodeFactory sqlNodeFactory) {
        this.sqlNodeFactory = sqlNodeFactory;
    }


    @Override
    public String getSql(SqlNode sqlNode) throws Exception {
        return getCustomSql(sqlNode).trim();
    }

    public abstract String getCustomSql(SqlNode sqlNode) throws Exception;
}
