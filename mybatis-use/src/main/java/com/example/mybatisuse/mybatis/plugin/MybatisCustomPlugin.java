package com.example.mybatisuse.mybatis.plugin;

import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Properties;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class MybatisCustomPlugin implements Interceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        LocalDateTime startTime = LocalDateTime.now();
        String sql = statementHandler.getBoundSql().getSql();
        String sqlId = null;

        if (statementHandler instanceof RoutingStatementHandler) {
            RoutingStatementHandler routing = (RoutingStatementHandler) statementHandler;
            Field[] fields = RoutingStatementHandler.class.getDeclaredFields();
            for (Field field1 : fields) {
                field1.setAccessible(true);
                Object f1 = field1.get(routing);
                if (f1 instanceof BaseStatementHandler) {
                    Field[] fields2 = BaseStatementHandler.class.getDeclaredFields();
                    for (Field field2 : fields2) {
                        field2.setAccessible(true);
                        Object f2 = field2.get(f1);

                        if (f2 instanceof MappedStatement) {
                            MappedStatement mapper = (MappedStatement) f2;
                            sqlId = mapper.getId();
                        }
                    }
                }
            }
        }

        logger.info(sql.trim());
        try {
            return invocation.proceed();
        } finally {
            LocalDateTime endTime = LocalDateTime.now();
            logger.info("sqlId:{},startTime:{},endTime:{},sql:{}", sqlId, startTime, endTime, sql);
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
