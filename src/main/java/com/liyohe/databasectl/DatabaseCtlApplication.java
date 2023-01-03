package com.liyohe.databasectl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DatabaseCtlApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseCtlApplication.class, args);
	}

}
