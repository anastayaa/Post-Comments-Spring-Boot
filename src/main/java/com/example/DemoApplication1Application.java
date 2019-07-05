package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example")
@ComponentScan({"com.example"})
@EnableJpaRepositories({"com.example.dao"})
@EnableJpaAuditing
public class DemoApplication1Application {

	private static final Logger logger = LogManager.getLogger(DemoApplication1Application.class);

	public static void main(String[] args) {
		logger.info("Entering application...");
		SpringApplication.run(DemoApplication1Application.class, args);
		logger.info("Exiting application...");
	}
}
