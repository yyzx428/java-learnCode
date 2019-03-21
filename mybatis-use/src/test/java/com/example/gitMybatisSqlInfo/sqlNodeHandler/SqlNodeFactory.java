package com.example.gitMybatisSqlInfo.sqlNodeHandler;

import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.scripting.xmltags.*;

import java.util.HashMap;
import java.util.Map;

public class SqlNodeFactory {

    private final Map<Class<?>, AbstractSqlNode> map;

    private GenericTokenParser parser;

    public SqlNodeFactory() {
        this.map = new HashMap<>();
        map.put(StaticTextSqlNode.class, new StaticTextSqlNodeImp(this));
        map.put(ChooseSqlNode.class, new ChooseSqlNodeImp(this));
        map.put(ForEachSqlNode.class, new ForEachSqlNodeImp(this));
        map.put(IfSqlNode.class, new IfSqlNodeImp(this));
        map.put(MixedSqlNode.class, new MixedSqlNodeImp(this));
        map.put(TrimSqlNode.class, new TrimSqlNodeImp(this));
        map.put(SetSqlNode.class, new TrimSqlNodeImp(this));
        map.put(WhereSqlNode.class, new WhereSqlNodeImp(this));
        map.put(TextSqlNode.class, new TextSqlNodeImp(this));

        parser = new GenericTokenParser("#{", "}", content -> " ? ");
    }

    public String choose(SqlNode sqlNode) throws Exception {
        if (sqlNode == null) {
            return "";
        }
        Class sqlNodeClass = sqlNode.getClass();

        if (!map.containsKey(sqlNodeClass)) {
            return "";
        }

        return parser.parse(map.get(sqlNodeClass).getSql(sqlNode).trim());
    }
}
