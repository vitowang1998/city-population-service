package com.example.citypopulationservice.controller;

import com.example.citypopulationservice.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/cities/{country}/{city}/population")
    public long getPopulation(
                @PathVariable("country") String country,
                @PathVariable("city") String city) {

            return cityService.getPopulation(country, city);
        }

    @PostMapping("countries/{country}/population-requests")
    public ResponseEntity<String> fanOutCountry(@PathVariable("country") String country) {
        String correlationId = cityService.fanOutCountry(country);

        return ResponseEntity.accepted().body("RequestId =  " + correlationId);
    }
}
