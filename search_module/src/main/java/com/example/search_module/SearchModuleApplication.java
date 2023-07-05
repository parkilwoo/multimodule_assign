package com.example.search_module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.example.data_module"})
@EntityScan(basePackages = {"com.example.data_module"})
public class SearchModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchModuleApplication.class, args);
	}

}
