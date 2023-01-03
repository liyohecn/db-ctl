package com.liyohe.databasectl.service;

import java.util.List;

public interface JdbcService {

    void query(String name, String sql);

    String getTableDdl(String name, String table);

    List<String> getTables(String name);

    List<List<String>> compareDatabase(String source, String target);

}
