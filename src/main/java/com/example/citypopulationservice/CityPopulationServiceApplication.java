package com.example.citypopulationservice;

import com.example.citypopulationservice.service.CityService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CityPopulationServiceApplication {

    public static void main(String[] args) {
        System.out.println("Hello, world");
        SpringApplication.run(CityPopulationServiceApplication.class, args);
    }

    @Bean
    ApplicationRunner verifyDatabase(CityService cityService) {
        return args -> {
            Long population = cityService.getPopulation("Canada", "Toronto");

            System.out.println("Toronto population = " + population);
        };
    }

}
