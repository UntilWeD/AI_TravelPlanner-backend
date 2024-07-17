package com.teamsix.firstteamproject.travelplan.service;


import com.teamsix.firstteamproject.travelplan.dto.amadeus.AmadeusAccessToken;
import com.teamsix.firstteamproject.travelplan.dto.amadeus.AmadeusCond;
import com.teamsix.firstteamproject.travelplan.dto.amadeus.FlightResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@Slf4j
public class AmadeusApiService {

    @Value("${amadeus.ApiKey}")
    private String apiKey;
    @Value("${amadeus.Secret}")
    private String secretKey;
    @Value("test.api.amadeus.com")
    private String baseUrl;

    private final WebClient flightWebClient;
    private AmadeusAccessToken currentToken;

    public AmadeusApiService(@Qualifier("flightWebClient")WebClient flightWebClient) {
        this.flightWebClient = flightWebClient;
    }

    // granttype, client_id, client_secret
    // x-www-form-urlencoded 타입

    // block을 쓰지않고 값을 꺼낼수는 없을까?
    // 동적 파라미터 미구현
    public Mono<FlightResponse> getFlightOffers(AmadeusCond cond){
        String accessToken = getAccessToken().block().getAccess_token();

        return flightWebClient.get()
                .uri(uriBuilder ->  uriBuilder
                        .path("/shopping/flight-destinations")
                        .queryParam("origin",cond.getOrigin())
                        .build())
                .header("Authorization", "Bearer "+accessToken)
                .retrieve()
                .bodyToMono(FlightResponse.class);
    }

    // 리팩토링 필요
    // 만료체크 로직 미구현
    private Mono<AmadeusAccessToken> getAccessToken(){
        if(currentToken == null || currentToken.isExpired()){
            log.info("[AmadeusApiService] getAccessToken Method is Starting!");
            return flightWebClient.post()
                    .uri("/security/oauth2/token")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .bodyValue("grant_type=client_credentials&client_id=" + apiKey + "&client_secret=" + secretKey)
                    .retrieve()
                    .bodyToMono(AmadeusAccessToken.class)
                    .doOnNext(token ->{
                        token.setIssuedAt(Instant.now());
                        currentToken = token;
                    });
        } else {
            return Mono.just(currentToken);
        }

    }




}
