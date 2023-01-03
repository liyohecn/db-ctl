package com.liyohe.databasectl.service.impl;

import com.liyohe.databasectl.properies.ConfigProp;
import com.liyohe.databasectl.service.ConfigService;
import com.liyohe.databasectl.util.YamlUtil;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@Service
public class ConfigServiceImpl implements ConfigService {

    private static final String CONFIG_FILE_NAME = "dbctl.yaml";

    @Resource
    private ApplicationHome home;

    @Override
    public ConfigProp getConfig() {
        String yamlStr = loadConfigFile();
        return YamlUtil.toObject(yamlStr, ConfigProp.class);
    }


    private String loadConfigFile() {
        String parent = home.getSource().getParentFile().getParent();
        File configFile = new File(parent + File.separator + CONFIG_FILE_NAME);
        StringBuilder sb = new StringBuilder();
        try {
            if (!configFile.exists()) {
                configFile.createNewFile();
            }

            Files.readAllLines(configFile.toPath()).stream().forEach(item -> sb.append(item).append("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }
}
