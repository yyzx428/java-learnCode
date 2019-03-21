package com.example.gitMybatisSqlInfo.test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlOutputVisitor;
import com.alibaba.druid.sql.dialect.sqlserver.parser.SQLServerStatementParser;
import com.alibaba.druid.sql.dialect.sqlserver.visitor.SQLServerOutputVisitor;
import com.example.gitMybatisSqlInfo.sqlNodeHandler.SqlNodeFactory;
import com.google.common.base.Splitter;
import io.shardingsphere.core.parsing.antlr.autogen.MySQLStatementLexer;
import io.shardingsphere.core.parsing.antlr.autogen.MySQLStatementParser;
import io.shardingsphere.core.parsing.antlr.extractor.impl.TableNamesExtractor;
import io.shardingsphere.core.parsing.antlr.sql.segment.table.TableSegment;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class BaseMybatisTest {

    private final static String path = "C:\\Users\\29410\\Desktop\\";
    private SqlSessionFactory sqlSessionFactory;
    private LinkedList<String> projectColumnsList;
    private LinkedList<String> subscribeColumnsList;
    private LinkedList<String> subscribeDetailColumnsList;
    private SqlNodeFactory sqlNodeFactory;
    private int successfulCount;
    private int failCount;

    private Map<String, List<Entity>> resultMap = new HashMap<>();

    @Before
    public void SetUp() throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(new DruidDataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath*:mapper*/**/*.xml"));
        sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource("classpath:mybatis-config.xml"));

        sqlSessionFactory = sessionFactory.getObject();

        projectColumnsList = getResultMap(path + "project.txt");
        subscribeColumnsList = getResultMap(path + "subscribe.txt");
        subscribeDetailColumnsList = getResultMap(path + "subscribeDetail.txt");

        sqlNodeFactory = new SqlNodeFactory();

        successfulCount = 0;
        failCount = 0;
    }

    public LinkedList<String> getResultMap(String path) throws IOException {
        File file = new File(path);
        try (FileInputStream stream = new FileInputStream(file)) {
            byte[] bytes = new byte[2000];
            stream.read(bytes);
            String projectColumns = new String(bytes, "UTF-8");
            return new LinkedList<>(Arrays.asList(projectColumns.split(System.lineSeparator())));
        }
    }

    @Test
    public void test() throws NoSuchFieldException, IOException {
        HashMap<String, StringBuilder> resultId = new HashMap<>();
        Configuration configuration = sqlSessionFactory.getConfiguration();
        Collection<MappedStatement> collection = configuration.getMappedStatements();
        List<String> parserFailSql = new LinkedList<>();
        Integer i = 0;
        for (Object mappedStatement : collection) {
            System.out.println(i++);
            if (!(mappedStatement instanceof MappedStatement)) {
                continue;
            }
            StringBuilder sqlStr = new StringBuilder();
            if (resultId.containsKey(((MappedStatement) mappedStatement).getId())) {
                continue;
            }
            SqlSource sqlSource = ((MappedStatement) mappedStatement).getSqlSource();
            if (sqlSource instanceof RawSqlSource) {
                Field field = RawSqlSource.class.getDeclaredField("sqlSource");
                ReflectionUtils.makeAccessible(field);
                sqlSource = (SqlSource) ReflectionUtils.getField(field, sqlSource);
            }

            if (sqlSource instanceof StaticSqlSource) {
                Field field = StaticSqlSource.class.getDeclaredField("sql");
                ReflectionUtils.makeAccessible(field);
                String sql = (String) ReflectionUtils.getField(field, sqlSource);
                sqlStr.append(sql.trim());
            }

            if (sqlSource instanceof DynamicSqlSource) {
                Field field = DynamicSqlSource.class.getDeclaredField("rootSqlNode");
                ReflectionUtils.makeAccessible(field);
                MixedSqlNode mixedSqlNode = (MixedSqlNode) ReflectionUtils.getField(field, sqlSource);
                Field field1 = MixedSqlNode.class.getDeclaredField("contents");
                ReflectionUtils.makeAccessible(field1);
                List<SqlNode> list = (List<SqlNode>) ReflectionUtils.getField(field1, mixedSqlNode);
                for (SqlNode sqlNode : list) {
                    try {
                        sqlStr.append(sqlNodeFactory.choose(sqlNode).trim()).append(" ");
                    } catch (Exception e) {
                        System.out.println(((MappedStatement) mappedStatement).getId() + ":获取sql异常" + e);
                        e.printStackTrace();
                    }
                }
            }

            try {
                execMySql(sqlStr.toString(), false);
                successfulCount++;
            } catch (Exception e) {
                failCount++;
                parserFailSql.add(((MappedStatement) mappedStatement).getId()
                        + ":" + System.lineSeparator() + System.lineSeparator() +
                        ((MappedStatement) mappedStatement).getSqlCommandType().name() + System.lineSeparator()
                        + sqlStr.toString());
            }

            Collection<TableSegment> tableSegments = processSqlReTableName(sqlStr.toString());
            if (!tableSegments.isEmpty()) {
                for (TableSegment tableSegment : tableSegments) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(tableSegment.getName());
                    if (tableSegment.getAlias().isPresent()) {
                        sb.append(" ,别名:").append(tableSegment.getAlias().get());
                    }
                    System.out.println(sb.toString());
                }
            }
            chooseRuleDate(tableSegments, "project_new", projectColumnsList, sqlStr.toString(), ((MappedStatement) mappedStatement).getId());

        }
        StringBuilder result = new StringBuilder();
        resultMap.forEach((x, y) -> {
            result.append(x).append(" :").append(System.lineSeparator());
            result.append(StringUtils.collectionToDelimitedString(y.stream().map(z -> z.name).collect(Collectors.toList()), ","));
            result.append(System.lineSeparator());
            result.append(System.lineSeparator());
            result.append(System.lineSeparator());
        });

        File file = new File(path + "ResultSql.txt");
        if (!file.exists()) {
            if (file.createNewFile()) {
                System.out.println("文件创建成功");
            }
        }

        try (FileOutputStream stream = new FileOutputStream(file)) {
            stream.write(result.toString().getBytes());
        }

        File file1 = new File(path + "FailSql.txt");
        if (!file.exists()) {
            if (file.createNewFile()) {
                System.out.println("文件创建成功");
            }
        }
        StringBuilder failSql = new StringBuilder();
        parserFailSql.forEach(x -> failSql.append(x).append(System.lineSeparator()).append(System.lineSeparator()));
        try (FileOutputStream stream = new FileOutputStream(file1)) {
            stream.write(failSql.toString().getBytes());
        }

        System.out.println(resultId.size());
        System.out.println("成功解析" + successfulCount + "条,解析失败" + failCount + "条");
    }

    private Collection<TableSegment> processSqlReTableName(String sql) {
        TokenStream tokenStream = new CommonTokenStream(new MySQLStatementLexer(CharStreams.fromString(sql)));
        MySQLStatementParser parser = new MySQLStatementParser(tokenStream);
        ParseTree parseTree = parser.execute().getChild(0);
        TableNamesExtractor extractor = new TableNamesExtractor();
        return extractor.extract((ParserRuleContext) parseTree);
    }

    private void chooseRuleDate(Collection<TableSegment> tableSegments, String tableName,
                                LinkedList<String> ruleDateList, String sqlStr,
                                String mappedStatementId) {
        long count = tableSegments.stream().filter(x -> x.getName().equalsIgnoreCase(tableName)).count();
        if (0 == count) {
            return;
        }

        for (String ruleDate : ruleDateList) {
            if (sqlStr.toLowerCase().contains(ruleDate.toLowerCase())) {
                String name = Splitter.on(".").trimResults().splitToList(mappedStatementId).get(5);
                String nameSql = Splitter.on(".").trimResults().splitToList(mappedStatementId).get(6);
                List<Entity> list = resultMap.computeIfAbsent(name, k -> new LinkedList<>());
                list.add(new Entity(nameSql, sqlStr));
            }
        }
    }

    private String execMySql(String sql, boolean isSqlServer) {
        StringBuilder out = new StringBuilder();
        List<SQLStatement> statementList;
        if (!isSqlServer) {
            MySqlOutputVisitor visitor = new MySqlOutputVisitor(out);
            MySqlStatementParser parser = new MySqlStatementParser(sql);
            statementList = parser.parseStatementList();
            for (SQLStatement statement : statementList) {
                statement.accept(visitor);
                visitor.println();
            }
        } else {
            SQLServerOutputVisitor visitor = new SQLServerOutputVisitor(out);
            SQLServerStatementParser parser = new SQLServerStatementParser(sql);
            statementList = parser.parseStatementList();
            for (SQLStatement statement : statementList) {
                statement.accept(visitor);
                visitor.println();
            }
        }

        System.out.println(out.toString());
        return out.toString();
    }

    static class Entity {
        private String name;
        private String sql;

        public Entity(String name, String sql) {
            this.name = name;
            this.sql = sql;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }
    }
}
