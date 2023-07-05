package com.example.popular_module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.example.data_module"})
@EntityScan(basePackages = {"com.example.data_module"})
public class PopularModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(PopularModuleApplication.class, args);
    }

}
