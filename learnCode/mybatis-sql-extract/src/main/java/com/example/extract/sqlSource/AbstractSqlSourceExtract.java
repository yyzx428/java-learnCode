package com.example.extract.sqlSource;

import com.example.extract.AbstractExtract;
import com.example.factory.SqlExtractFactory;
import org.apache.ibatis.mapping.SqlSource;

public abstract class AbstractSqlSourceExtract extends AbstractExtract<SqlSource, String> {
    public AbstractSqlSourceExtract(SqlExtractFactory<SqlSource, String> factory) {
        super(factory);
    }
}
