package com.example.citypopulationservice.kafka;

import com.example.citypopulationservice.repository.CityRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CityTaskConsumer {

    private final CityRepository cityRepository;

    public CityTaskConsumer(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @KafkaListener(topics = "city-population-requests")
    public void handle(String message) {

        String[] parts = message.split(",", 2);

        String country = parts[0].trim();
        String city = parts[1].trim();

        long population = cityRepository.getPopulation(country, city);

        System.out.println("Kafka worker returns: " + city + " population = " + population);
    }


}
