package com.liyohe.databasectl.config;


import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbctlConfiguration {

    @Bean
    public ApplicationHome getHome(){
        return new ApplicationHome(this.getClass());
    }



}
