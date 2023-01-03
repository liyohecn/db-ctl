package com.liyohe.databasectl.properies;

import java.util.Map;

public class ConfigProp {


    private Map<String, DataSource> datasource;

    public Map<String, DataSource> getDatasource() {
        return datasource;
    }

    public void setDatasource(Map<String, DataSource> datasource) {
        this.datasource = datasource;
    }

    public static class DataSource {

        private String driver;

        private String url;

        private String username;

        private String password;

        public String getDriver() {
            return driver;
        }

        public void setDriver(String driver) {
            this.driver = driver;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "DataSource{" +
                    "driver='" + driver + '\'' +
                    ", url='" + url + '\'' +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "ConfigProp{" +
                "dataSource=" + datasource.toString() +
                '}';
    }
}
