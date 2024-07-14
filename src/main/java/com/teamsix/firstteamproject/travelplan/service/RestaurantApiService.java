package com.teamsix.firstteamproject.travelplan.service;

import com.teamsix.firstteamproject.travelplan.dto.Restaurant.RestaurantCond;
import com.teamsix.firstteamproject.travelplan.util.WebClientUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class RestaurantApiService {



    private String serviceKey = "f2e31414-ca97-44d0-bd13-847862a15093";


    public String getRestaurantDetails(RestaurantCond rCond){

        WebClient webClient = WebClientUtil.getBaseUrl("http://api.kcisa.kr/openapi/API_CNV_063/request");

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("serviceKey", serviceKey)
                        .queryParam("numOfRows", rCond.getNumOfRows())
                        .queryParam("pageNo", rCond.getPageNo())
                        .queryParam("areaNm", rCond.getAreaNm())
                        .queryParam("clNm", rCond.getClNm()).build())
                .retrieve()
                .bodyToMono(String.class)
                .block();


    }

}
