package com.mtjb.examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class ExamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamplesApplication.class, args);
	}

}
