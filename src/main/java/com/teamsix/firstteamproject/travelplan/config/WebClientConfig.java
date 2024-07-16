package com.teamsix.firstteamproject.travelplan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient flightWebClient(){
        return WebClient.builder()
                .baseUrl("https://test.api.amadeus.com/v1")
                .build();
    }
}
