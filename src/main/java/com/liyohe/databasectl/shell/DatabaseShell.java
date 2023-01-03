package com.liyohe.databasectl.shell;

import com.liyohe.databasectl.service.ConsoleService;
import com.liyohe.databasectl.service.JdbcService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class DatabaseShell {

    @Resource
    private JdbcService jdbcService;

    @Resource
    private ConsoleService consoleService;

    @ShellMethod(key = "dbctl show config", value = "查看配置的数据库")
    public void showConfig() {
        System.out.println("======");
    }

    @ShellMethod(key = "dbctl show database", value = "查看数据库")
    public void showDatabase(String name, String database) {
        System.out.println(name + "------" + database);
    }

    @ShellMethod(key = "dbctl show table", value = "查看表")
    public void showTable(String name) {
        List<String> tables = jdbcService.getTables(name);
        List<List<String>> rows = tables.stream().map(row -> Arrays.asList(row)).collect(Collectors.toList());
        consoleService.printTable(rows, "TABLES");
    }

    @ShellMethod(key = "dbctl exec", value = "执行sql")
    public void exec(String name, String sql) {
        System.out.println(name + ":" + sql);
    }

    @ShellMethod(key = "dbctl compare database", value = "执行sql")
    public void compareDatabase(String source, String target) {
        List<List<String>> lists = jdbcService.compareDatabase(source, target);
        consoleService.printTable(lists, source, target, "exchange");
    }
}
