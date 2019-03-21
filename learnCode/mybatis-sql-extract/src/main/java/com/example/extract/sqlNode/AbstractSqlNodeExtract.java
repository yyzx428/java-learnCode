package com.example.extract.sqlNode;

import com.example.extract.AbstractExtract;
import com.example.factory.SqlExtractFactory;
import org.apache.ibatis.scripting.xmltags.SqlNode;

public abstract class AbstractSqlNodeExtract extends AbstractExtract<SqlNode, String> {

    public AbstractSqlNodeExtract(SqlExtractFactory<SqlNode, String> factory) {
        super(factory);
    }

    @Override
    public String getSql(SqlNode source) throws Exception {
        return getCustomSql(source).trim();
    }

    protected abstract String getCustomSql(SqlNode source) throws Exception;
}
