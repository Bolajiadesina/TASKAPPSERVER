package com.moj.taskAppServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TaskAppServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskAppServerApplication.class, args);
	}

}
