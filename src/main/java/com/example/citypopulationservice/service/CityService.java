package com.example.citypopulationservice.service;

import com.example.citypopulationservice.repository.CityRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public CityService(CityRepository cityRepository,  KafkaTemplate kafkaTemplate) {
        this.cityRepository = cityRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public long getPopulation(String country, String city) {
        return cityRepository.getPopulation(country, city);
    }

    public List<String> getCitiesByCountry(String country) {
        return cityRepository.getCitiesByCountry(country);
    }

    public String fanOutCountry(String country) {
        String topic = "city-population-requests";
        String correlationId = UUID.randomUUID().toString();

        List<String> cities = cityRepository.getCitiesByCountry(country);

        for (String city : cities) {
            String message = correlationId + "," + country + "," + city;

            kafkaTemplate.send(topic, message);
        }

        return correlationId;
    }
}
