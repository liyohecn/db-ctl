package com.liyohe.databasectl.service;

import java.util.List;

public interface ConsoleService {


    void printTable(List<List<String>> table);

    void printTable(List<List<String>> table,Object ...columns);
}
