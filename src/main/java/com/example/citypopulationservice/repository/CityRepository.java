package com.example.citypopulationservice.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CityRepository{

    private final JdbcTemplate jdbcTemplate;

    public CityRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long getPopulation(String country, String city) {
        String sql = """
            SELECT population
            FROM city_population
            WHERE country = ? AND city = ?
            """;

        return jdbcTemplate.queryForObject(
            sql,
            Long.class,
            country,
            city
        );
    }
}