package com.liyohe.databasectl.service.impl;

import com.liyohe.databasectl.consoletable.ConsoleTable;
import com.liyohe.databasectl.consoletable.table.Cell;
import com.liyohe.databasectl.service.ConsoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ConsoleServiceImpl implements ConsoleService {
    @Override
    public void printTable(List<List<String>> table) {
        List<List<Cell>> body = table.stream().map(row -> row.stream().map(Cell::new).collect(Collectors.toList())).collect(Collectors.toList());
        new ConsoleTable.ConsoleTableBuilder().addRows(body).build().print();
    }

    @Override
    public void printTable(List<List<String>> table, Object... columns) {
        List<Cell> header = Arrays.stream(columns).map(item -> new Cell(String.valueOf(item))).collect(Collectors.toList());
        List<List<Cell>> body = table.stream().map(row -> row.stream().map(Cell::new).collect(Collectors.toList())).collect(Collectors.toList());
        new ConsoleTable.ConsoleTableBuilder().addHeaders(header).addRows(body).build().print();
    }
}
