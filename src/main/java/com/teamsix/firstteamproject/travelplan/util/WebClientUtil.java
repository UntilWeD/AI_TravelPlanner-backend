package com.teamsix.firstteamproject.travelplan.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientUtil {

    public static WebClient getBaseUrl(final String url){
        return WebClient.builder()
                        .baseUrl(url)
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build()
                .mutate().build();
    }
}
