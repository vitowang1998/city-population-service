package com.example.citypopulationservice;

import com.example.citypopulationservice.repository.CityRepository;
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
    ApplicationRunner verifyDatabase(JdbcTemplate jdbcTemplate, CityRepository cityRepository) {
        return args -> {
            Long population = cityRepository.getPopulation("Canada", "Toronto");

            System.out.println("Toronto population = " + population);
        };
    }

}
