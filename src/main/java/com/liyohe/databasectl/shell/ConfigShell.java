package com.liyohe.databasectl.shell;

import com.liyohe.databasectl.properies.ConfigProp;
import com.liyohe.databasectl.service.ConfigService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.annotation.Resource;
import java.util.Objects;

@ShellComponent
public class ConfigShell {
    @Resource
    private ConfigService configService;

    @ShellMethod(key = "config show", value = "配置")
    public void config(String name) {
        if (Objects.equals(name, "--all")) {
            ConfigProp config = configService.getConfig();
            System.out.println(config.toString());
        } else {
            System.out.println(name);
        }
    }


    @ShellMethod(key = "config set", value = "设置属性")
    public void set(String name, String value) {
        System.out.println(name + "：" + value);
    }

    @ShellMethod(key = "config get", value = "获取属性")
    public void get(String name) {
        if (Objects.equals(name, "--all")) {
            System.out.println("name");
            System.out.println("age");
        } else {
            System.out.println(name);
        }
    }
}
