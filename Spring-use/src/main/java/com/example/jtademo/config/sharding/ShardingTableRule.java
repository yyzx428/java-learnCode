package com.example.jtademo.config.sharding;

import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ShardingTableRule {

    public static final int TABLE_COUNT = 16;

    private String logicTableName;

    private List<String> databaseName;

    private List<String> actualTableNames;

    public ShardingTableRule(String logicTableName, List<String> databaseName) {
        this.logicTableName = logicTableName;
        this.databaseName = databaseName;
        actualTableNames = IntStream.range(0, TABLE_COUNT).mapToObj(x -> logicTableName + "_" + x).collect(Collectors.toList());
    }

    public static int idToTableIndex(String id) {
        return Integer.valueOf(id) % TABLE_COUNT;
    }

    public static void main(String[] args) {
        System.out.println(idToTableIndex("100"));
    }

    public String getLogicTableName() {
        return logicTableName;
    }

    public String getActualTableNamesStr() {
        StringBuilder st = new StringBuilder();
        for (String name : databaseName) {
            st.append(StringUtils.collectionToDelimitedString(actualTableNames.stream()
                    .map(x -> name + "." + x).collect(Collectors.toList()), ","));
            st.append(",");
        }
        return st.toString();
    }

}
