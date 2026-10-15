package com.example.citypopulationservice.service;

import com.example.citypopulationservice.kafka.CityResultAggregator;
import com.example.citypopulationservice.repository.CityRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final CityResultAggregator resultAggregator;

    public CityService(
            CityRepository cityRepository,
            KafkaTemplate<String, String> kafkaTemplate,
            CityResultAggregator resultAggregator
    ) {
        this.cityRepository = cityRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.resultAggregator = resultAggregator;
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
        resultAggregator.register(correlationId, cities.size());

        for (String city : cities) {
            String message = correlationId + "," + country + "," + city;

            kafkaTemplate.send(topic, message);
        }

        return correlationId;
    }
}
