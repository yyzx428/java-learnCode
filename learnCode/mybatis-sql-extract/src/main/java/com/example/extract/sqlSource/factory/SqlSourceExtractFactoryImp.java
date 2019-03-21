package com.example.extract.sqlSource.factory;

import com.example.extract.sqlSource.AbstractSqlSourceExtract;
import com.example.extract.sqlSource.DynamicSqlSourceExtract;
import com.example.extract.sqlSource.RawSqlSourceExtract;
import com.example.extract.sqlSource.StaticSqlSourceExtract;
import com.google.common.collect.Lists;

import java.util.List;

public class SqlSourceExtractFactoryImp extends AbstractSqlSourceExtractFactory {

    @Override
    protected List<AbstractSqlSourceExtract> initExacts() {
        return Lists.newArrayList(
                new StaticSqlSourceExtract(this),
                new DynamicSqlSourceExtract(this),
                new RawSqlSourceExtract(this)
        );
    }
}
