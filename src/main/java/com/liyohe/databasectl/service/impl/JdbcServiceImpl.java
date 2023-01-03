package com.liyohe.databasectl.service.impl;

import com.liyohe.databasectl.properies.ConfigProp;
import com.liyohe.databasectl.service.JdbcService;
import com.liyohe.databasectl.service.ConsoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class JdbcServiceImpl implements JdbcService {

    private static final String SHOW_TABLES = "SHOW TABLES;";

    private static final String SHOW_CREATE_TABLE = "SHOW CREATE TABLE ";

    @Resource
    private ConfigServiceImpl configService;
    @Resource
    private ConsoleService consoleService;

    @Override
    public void query(String name, String sql) {
        Connection connection = getConnection(name);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            List<String> columns = new ArrayList<>();
            List<List<Object>> table = new ArrayList<>();
            ResultSetMetaData metaData = resultSet.getMetaData();

            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                columns.add(metaData.getColumnLabel(i));
            }

            while (resultSet.next()) {
                List<Object> row = new ArrayList<>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    row.add(resultSet.getObject(i));
                }
                table.add(row);
            }

//            consoleService.printTable(table, columns.toArray());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String getTableDdl(String name, String table) {
        Connection connection = getConnection(name);
        String ddl = null;
        try {
            PreparedStatement statement = connection.prepareStatement(SHOW_CREATE_TABLE + table);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ddl = resultSet.getString(2);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return ddl;
    }

    @Override
    public List<String> getTables(String name) {
        List<String> tables = new ArrayList<>();
        Connection connection = getConnection(name);
        try {
            PreparedStatement statement = connection.prepareStatement(SHOW_TABLES);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tables.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return tables;
    }

    @Override
    public List<List<String>> compareDatabase(String source, String target) {
        List<String> sourceTable = getTables(source);
        List<String> targetTable = getTables(target);
        List<List<String>> compareTable = new ArrayList<>();
        Set<String> all = new HashSet<>();
        all.addAll(sourceTable);
        all.addAll(targetTable);
        List<String> sortTable = all.stream().sorted().collect(Collectors.toList());

        sortTable.stream().forEach(item -> {
            List<String> row = new ArrayList<>();
            String st = sourceTable.contains(item) ? item : "-";
            String tt = targetTable.contains(item) ? item : "-";
            row.add(st);
            row.add(tt);
            if (Objects.equals(st, tt)) {
                String stDdl = getTableDdl(source, st);
                String ttDdl = getTableDdl(target, tt);
                String same = Objects.equals(stDdl, ttDdl) ? "" : "*";
                row.add(same);
            } else {
                row.add("");
            }
            compareTable.add(row);
        });

        return compareTable;
    }


    public Connection getConnection(String name) {
        ConfigProp config = configService.getConfig();
        Assert.notNull(config.getDatasource(), "数据源未配置！");
        ConfigProp.DataSource dataSource = config.getDatasource().get(name);
        Assert.notNull(dataSource, name + "数据源配置未找到！");

        loadDriver(dataSource.getDriver());
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }


    /**
     * 加载驱动
     */
    private static void loadDriver(String driver) {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
