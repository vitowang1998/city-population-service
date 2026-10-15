package com.example.citypopulationservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CityResultAggregator {

    private static class RequestState {
        final int expectedCount;
        final Map<String, Long> results = new LinkedHashMap<>();

        RequestState(int expectedCount) { this.expectedCount = expectedCount; }
    }

    private final Map<String, RequestState> requests = new ConcurrentHashMap<>();

    public void register(String correlationId, int expectedCount) {
        requests.put(correlationId, new RequestState(expectedCount));

        if (expectedCount == 0) {
            System.out.println("Fan-in completed: {}");
        }
    }

    @KafkaListener(
            topics = "city-population-results",
            groupId = "city-population-aggregators"
    )
    public void handle(String message) throws Exception {
        String[] parts = message.split(",", 3);

        if (parts.length != 3) {
            System.out.println("Invalid result: " + message);
            return;
        }

        String correlationId = parts[0].trim();
        String city = parts[1].trim();
        long population = Long.parseLong(parts[2].trim());

        RequestState state = requests.get(correlationId);

        if (state == null) {
            System.out.println("Unknown request: " + correlationId);
            return;
        }

        synchronized (state) {
            Long previous = state.results.putIfAbsent(city, population);

            if (previous == null && state.results.size() == state.expectedCount) {
                System.out.println("Fan-in completed: " + state.results);
            }
        }
    }
}