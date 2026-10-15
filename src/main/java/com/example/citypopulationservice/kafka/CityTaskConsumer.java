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

        String[] parts = message.split(",", 3);

        // Temporarily skip old-format messages
        if (parts.length != 3) {
            System.out.println(
                    "Skipping old/invalid message: " + message
            );
            return;
        }

        String correlationId = parts[0].trim();
        String country = parts[1].trim();
        String city = parts[2].trim();

        long population = cityRepository.getPopulation(country, city);

        System.out.println("Request = " + correlationId + ": " + city + " population = " + population);
    }


}
