package com.teamsix.firstteamproject.travelplan.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamsix.firstteamproject.travelplan.dto.gpt.DomesticTravelRequest;
import com.teamsix.firstteamproject.travelplan.dto.gpt.InternationalTravelRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GPTService {

    @Value("${gpt.api.key}")
    private String GPT_API_KEY;


    public Map<String, Object> getDomesticTravel(DomesticTravelRequest requestDTO) {
        String prompt = generateDomesticPrompt(requestDTO);
        List<Map<String, String>> recommendations = callChatGPT(prompt);

        Map<String, Object> response = new HashMap<>();
        response.put("recommendations", recommendations);
        return response;
    }

    public Map<String, Object> getInternationalTravel(InternationalTravelRequest requestDTO) {
        String prompt = generateInternationalPrompt(requestDTO);
        List<Map<String, String>> recommendations = callChatGPT(prompt);

        Map<String, Object> response = new HashMap<>();
        response.put("recommendations", recommendations);


        return response;
    }

    private List<Map<String, String>> callChatGPT(String prompt) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(GPT_API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", new Object[]{Map.of("role", "user", "content", prompt)});
        requestBody.put("max_tokens", 500);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
        Map<String, Object> responseBody = response.getBody();

        String content = ((Map)((Map)((List)responseBody.get("choices")).get(0)).get("message")).get("content").toString().trim();

        //JSON 문자열을 JAVA 객체로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, String>> recommendations;
        try {
            recommendations = objectMapper.readValue(content, List.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse response from GPT-3", e);
        }

        return recommendations;
    }

    private String generateDomesticPrompt(DomesticTravelRequest request) {
        return String.format(
                "한국 내에서 %s와(과) 함께 여행하기 좋은 도시 3곳을 추천해주세요. " +
                        "출발지는 %s이며, " +
                        "여행 기간은 %s부터 %s까지입니다. " +
                        "주로 %s를 이용할 예정이며, %s 스타일의 여행을 선호합니다. " +
                        "각 도시명과 그 도시를 추천하는 이유를 함께 설명해주세요. " +
                        "응답은 다음과 같은 JSON 형식으로 작성해주세요: " +
                        "[{\"city\": \"도시명\", \"reason\": \"추천 이유\"}, " +
                        "{\"city\": \"도시명\", \"reason\": \"추천 이유\"}, " +
                        "{\"city\": \"도시명\", \"reason\": \"추천 이유\"}]",
                request.getCompanions(), request.getDepartureCity(),
                request.getStartDate(), request.getEndDate(),
                request.getTransportation(), request.getStyle()
        );
    }

    private String generateInternationalPrompt(InternationalTravelRequest request) {
        return String.format(
                "당신은 여행 어시스턴트입니다. 다음 조건에 맞는 3개의 도시를 추천하고, 각 도시에 대한 추천 이유를 설명해주세요: " +
                        "\n- 예산: 전체 여행에 %s USD" +
                        "\n- 여행자: %s세의 %s, %s와(과) 함께 여행" +
                        "\n- 여행 기간: %s부터 %s까지" +
                        "\n- 선호 사항: 여행자는 %s 유형의 목적지를 선호함" +
                        "\n- 출발 도시: %s" +
                        "\n출발 도시, 예산, 비행 시간, 선호도를 고려하여 이 여행에 가장 적합한 도시 목적지 3곳을 추천해주세요." +
                        "\n응답은 다음과 같은 JSON 형식으로 한글로 작성해주세요: " +
                        "[{\"city\": \"도시명\", \"reason\": \"추천 이유\"}, " +
                        "{\"city\": \"도시명\", \"reason\": \"추천 이유\"}, " +
                        "{\"city\": \"도시명\", \"reason\": \"추천 이유\"}]",
                request.getBudget(), request.getAge(), request.getGender(), request.getCompanions(),
                request.getStartDate(), request.getEndDate(), request.getPreference(), request.getDepartureCity()
        );
    }
}
