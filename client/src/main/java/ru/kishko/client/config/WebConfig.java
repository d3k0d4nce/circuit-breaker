package ru.kishko.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration(value = "myConfig")
public class WebConfig {

    @Bean
    public WebClient webReviewClient() {
        return WebClient.builder().baseUrl("http://localhost:8081/reviews").build();
    }

}
