package com.teamsix.firstteamproject.travelplan.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamsix.firstteamproject.travelplan.dto.AreaCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class KoreaTourService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    public KoreaTourService(RestTemplateBuilder builder, ObjectMapper objectMapper) {
        this.restTemplate = builder.build();
        this.objectMapper = objectMapper;
    }

    public AreaCodeResponse getAreaCode() {


        try{
            URI uri = new URI("https://apis.data.go.kr/B551011/KorService1/areaCode1?numOfRows=30&pageNo=1&MobileOS=ETC&MobileApp=Apptest&_type=json&serviceKey=DaidnR1kJF8Bhgfg%2BPm7KThvGG%2FfhGGQ9MlU10xuE%2FDhbTeQ2ZX3NNEJwVx2T9GsgG7XoyoOdi1ca7KQdlA1gQ%3D%3D");
            String jsonResponse = restTemplate.getForObject(uri, String.class);
            log.info("api 응답"+jsonResponse);
            AreaCodeResponse areaCodeResponse = objectMapper.readValue(jsonResponse, AreaCodeResponse.class);
            return areaCodeResponse;
        } catch (Exception e){
            log.info("오류발생");
            e.printStackTrace();
            return null;
        }

    }


}
