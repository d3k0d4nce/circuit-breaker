package ru.kishko.client.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final WebClient webReviewClient;

    public String create(String description, Double mark) {
        return webReviewClient.get()
                .uri(uriBuilder -> uriBuilder.path("/create")
                        .queryParam("description", description)
                        .queryParam("mark", mark)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
