package com.example.jtademo.config.sharding;

import com.beust.jcommander.internal.Lists;
import com.example.jtademo.dataSource.DataSourceHolder;
import io.shardingsphere.api.config.rule.ShardingRuleConfiguration;
import io.shardingsphere.api.config.rule.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.ComplexShardingStrategyConfiguration;
import io.shardingsphere.core.constant.properties.ShardingPropertiesConstant;
import io.shardingsphere.core.rule.ShardingRule;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.*;

public class CurrentShardingDataSourceFactoryBean extends ShardingDataSourceFactoryBean {

    private static List<String> shardingKeys = Lists.newArrayList("classseMate", "student");

    @Override
    protected Properties createProperties() {
        Properties properties = new Properties();
        properties.setProperty(ShardingPropertiesConstant.SQL_SHOW.getKey(), "true");
        return properties;
    }

    @Override
    protected Map<String, Object> createConfigMap() {
        return Collections.EMPTY_MAP;
    }

    @Override
    protected ShardingRule createShardingRule() {
        ShardingRuleConfiguration configuration = new ShardingRuleConfiguration();

        ShardingTableRule studentTable = new ShardingTableRule("student", Lists.newArrayList(DataSourceHolder.dev_short));
        ShardingTableRule templateTable = new ShardingTableRule("template", Lists.newArrayList(DataSourceHolder.dev_short));
        ShardingTableRule classMetaTable = new ShardingTableRule("class_meta", Lists.newArrayList(DataSourceHolder.test_short));

        TableRuleConfiguration studentTableRule = new TableRuleConfiguration();
        studentTableRule.setLogicTable(studentTable.getLogicTableName());
        studentTableRule.setActualDataNodes(studentTable.getActualTableNamesStr());

        TableRuleConfiguration templateTableRule = new TableRuleConfiguration();
        templateTableRule.setLogicTable(templateTable.getLogicTableName());
        templateTableRule.setActualDataNodes(templateTable.getActualTableNamesStr());

        TableRuleConfiguration classMetaTableRule = new TableRuleConfiguration();
        classMetaTableRule.setLogicTable(classMetaTable.getLogicTableName());
        classMetaTableRule.setActualDataNodes(classMetaTable.getActualTableNamesStr());

        configuration.setTableRuleConfigs(Lists.newArrayList(studentTableRule, classMetaTableRule, templateTableRule));

        configuration.setDefaultDatabaseShardingStrategyConfig(
                new ComplexShardingStrategyConfiguration(
                        StringUtils.collectionToDelimitedString(shardingKeys, ","),
                        new DatabaseShardingAlgorithm()));

        configuration.setDefaultTableShardingStrategyConfig(
                new ComplexShardingStrategyConfiguration(StringUtils.collectionToDelimitedString(shardingKeys, ","),
                        new TableShardingAlgorithm()));

        configuration.setBindingTableGroups(Lists.newArrayList("student", "class_meta"));

        return new ShardingRule(configuration,
                Collections.unmodifiableList(Lists.newArrayList(DataSourceHolder.dev_short, DataSourceHolder.test_short)));
    }

    @Override
    protected Map<String, DataSource> createMap() {
        return createDataSourceMap();
    }

    private Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>();
        result.put(DataSourceHolder.dev_short, beanFactory.getBean(DataSourceHolder.dev, DataSource.class));
        result.put(DataSourceHolder.test_short, beanFactory.getBean(DataSourceHolder.test, DataSource.class));
        return result;
    }

}
