package com.innogy.spring.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringIntegrationWorksApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringIntegrationWorksApplication.class, args);
	}
}
