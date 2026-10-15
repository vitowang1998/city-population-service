package com.example.citypopulationservice.kafka;

import com.example.citypopulationservice.repository.CityRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CityTaskConsumer {

    private final CityRepository cityRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public CityTaskConsumer(CityRepository cityRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.cityRepository = cityRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "city-population-requests")
    public void handle(String message) throws Exception {
        final String resultTopic = "city-population-results";

        String[] parts = message.split(",", 3);
        String correlationId = parts[0].trim();
        String country = parts[1].trim();
        String city = parts[2].trim();
        long population = cityRepository.getPopulation(country, city);

        String resultMessage = correlationId + "," + city + "," + population;

        kafkaTemplate.send(resultTopic, correlationId, resultMessage).get(10, TimeUnit.SECONDS);

        System.out.println("Sending result of correlationId = " +
                correlationId + " - " + city + " population = " + population + " to result topic");
    }


}
