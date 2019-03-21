package com.example.jtademo.transactionFactory;

import com.example.jtademo.dataSource.DataSourceHolder;
import org.apache.ibatis.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MultipleManagedTransaction implements Transaction {

    private final Logger LOGGER = LoggerFactory.getLogger(MultipleManagedTransaction.class);

    private final DataSource dataSource;

    private Connection defaultConnection;

    private String defaultDatabaseIdentification;

    private ConcurrentMap<String, Connection> otherConnectionMap;

    private boolean isConnectionTransactional;

    private boolean autoCommit;

    public MultipleManagedTransaction(DataSource dataSource) {
        this.dataSource = dataSource;
        otherConnectionMap = new ConcurrentHashMap<>();
        defaultDatabaseIdentification = DataSourceHolder.dev;
    }

    @Override
    public Connection getConnection() throws SQLException {
        String dataBaseId = DataSourceHolder.getCurrentSource();
        if (dataBaseId.equals(defaultDatabaseIdentification)) {
            if (null != defaultConnection) {
                return defaultConnection;
            }
            this.defaultConnection = openConnection();
            this.autoCommit = defaultConnection.getAutoCommit();
            this.isConnectionTransactional = DataSourceUtils.isConnectionTransactional(defaultConnection, this.dataSource);
            return defaultConnection;
        } else {
            if (!otherConnectionMap.containsKey(dataBaseId)) {
                Connection connection = dataSource.getConnection();
                otherConnectionMap.putIfAbsent(dataBaseId, connection);
            }
            return otherConnectionMap.get(dataBaseId);
        }
    }

    private Connection openConnection() throws SQLException {
        return DataSourceUtils.getConnection(this.dataSource);
    }

    @Override
    public void commit() throws SQLException {
        if (this.defaultConnection != null && !this.isConnectionTransactional && !this.autoCommit) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Committing JDBC Connection [" + this.defaultConnection + "]");
            }
            this.defaultConnection.commit();
            for (Connection connection : otherConnectionMap.values()) {
                connection.commit();
            }
        }
    }

    @Override
    public void rollback() throws SQLException {
        if (this.defaultConnection != null && !this.isConnectionTransactional && !this.autoCommit) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Rolling JDBC Connection [" + this.defaultConnection + "]");
            }
            this.defaultConnection.rollback();
            for (Connection connection : otherConnectionMap.values()) {
                connection.rollback();
            }
        }
    }

    @Override
    public void close() throws SQLException {
        DataSourceUtils.releaseConnection(this.defaultConnection, this.dataSource);
        for (Connection connection : otherConnectionMap.values()) {
            DataSourceUtils.releaseConnection(connection, this.dataSource);
        }
    }

    @Override
    public Integer getTimeout() throws SQLException {
        ConnectionHolder holder = (ConnectionHolder) TransactionSynchronizationManager.getResource(dataSource);
        if (holder != null && holder.hasTimeout()) {
            return holder.getTimeToLiveInSeconds();
        }
        return null;
    }

}
