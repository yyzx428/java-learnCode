package com.example.extract.sqlNode.factory;

import com.example.extract.sqlNode.*;
import com.google.common.collect.Lists;
import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.scripting.xmltags.SqlNode;

import java.util.List;

public class SqlNodeExtractFactoryImp extends AbstractSqlNodeExtractFactory {

    private GenericTokenParser parser;

    public SqlNodeExtractFactoryImp() {
        parser = new GenericTokenParser("#{", "}", content -> " ? ");
    }

    @Override
    protected List<AbstractSqlNodeExtract> initExacts() {
        return Lists.newArrayList(
                new ChooseSqlNodeExtract(this),
                new ForEachSqlNodeExtract(this),
                new IfSqlNodeExtract(this),
                new MixedSqlNodeExtract(this),
                new StaticTextSqlNodeExtract(this),
                new TextSqlNodeExtract(this),
                new TrimSqlNodeExtract(this),
                new WhereSqlNodeExtract(this)
        );
    }

    @Override
    public String choose(SqlNode source) throws Exception {
        return parser.parse(super.choose(source));
    }
}
