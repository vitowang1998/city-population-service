package com.example.citypopulationservice;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class CityPopulationServiceApplication {

    public static void main(String[] args) {
        System.out.println("Hello, world");
        SpringApplication.run(CityPopulationServiceApplication.class, args);
    }

    @Bean
    ApplicationRunner verifyDatabase(JdbcTemplate jdbcTemplate) {
        return args -> {
            Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM city_population",
                    Integer.class
            );

            System.out.println("City count = " + count);
        };
    }

}
