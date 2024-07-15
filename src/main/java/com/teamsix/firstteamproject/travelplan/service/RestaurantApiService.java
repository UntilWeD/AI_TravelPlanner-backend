package com.teamsix.firstteamproject.travelplan.service;

import com.teamsix.firstteamproject.travelplan.dto.Restaurant.RestaurantCond;
import com.teamsix.firstteamproject.travelplan.dto.Restaurant.RestaurantDto;
import com.teamsix.firstteamproject.travelplan.dto.Restaurant.RestaurantResponse;
import com.teamsix.firstteamproject.travelplan.util.WebClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.util.UriComponentsBuilder;


@Service
@Slf4j
public class RestaurantApiService {

    @Value("${restaurantApi.serviceKey}")
    private String serviceKey;

    @Value("${restaurantApi.baseUrl}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public RestaurantApiService(RestTemplateBuilder builder) {
        restTemplate = builder.build();
    }

    public RestaurantResponse getRestaurantDetails(RestaurantCond rCond){

        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", rCond.getNumOfRows())
                .queryParam("pageNo", rCond.getPageNo())
                .queryParam("areaNm", rCond.getAreaNm())
                .queryParam("clNm", rCond.getClNm())
                .build().toUriString();


        return restTemplate.getForObject(url, RestaurantResponse.class);


    }


    /*
    public void getRestaurantDetails(RestaurantCond rCond){

        WebClient webClient = WebClientUtil.getBaseUrl("http://api.kcisa.kr/openapi/API_CNV_063/request");

        Mono<RestaurantResponse> restaurantResponseMonoList = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("numOfRows", rCond.getNumOfRows())
                        .queryParam("pageNo", rCond.getPageNo())
                        .queryParam("areaNm", rCond.getAreaNm())
                        .queryParam("clNm", rCond.getClNm()).build())
                .retrieve()
                .bodyToMono(RestaurantResponse.class);

        Flux<RestaurantDto> restaurantFlux = restaurantResponseMonoList
                .flatMapIterable(response -> response.getBody().getItems().getItem());


    }
    */

}
