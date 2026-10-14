package com.example.citypopulationservice.service;

import com.example.citypopulationservice.repository.CityRepository;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private final CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public long getPopulation(String country, String city) {
        return cityRepository.getPopulation(country, city);
    }
}
