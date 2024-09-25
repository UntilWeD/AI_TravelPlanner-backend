package com.teamsix.firstteamproject.travelplan.service;

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
        String cityName = callChatGPT(prompt);

        Map<String, Object> response = new HashMap<>();
        response.put("cityName", cityName);
        return response;
    }

    public Map<String, Object> getInternationalTravel(InternationalTravelRequest requestDTO) {
        String prompt = generateInternationalPrompt(requestDTO);
        String cityName = callChatGPT(prompt);

        Map<String, Object> response = new HashMap<>();
        response.put("cityName", cityName);

        return response;
    }

    private String callChatGPT(String prompt) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(GPT_API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", new Object[]{Map.of("role", "user", "content", prompt)});
        requestBody.put("max_tokens", 150);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
        Map<String, Object> responseBody = response.getBody();

        return ((Map)((Map)((List)responseBody.get("choices")).get(0)).get("message")).get("content").toString().trim();
    }

    private String generateDomesticPrompt(DomesticTravelRequest request) {
        return String.format(
                "Recommend a specific city in South Korea for someone traveling with %s. " +
                        "They will be departing from %s, " +
                        "and will be traveling from %s to %s. " +
                        "They prefer to use %s and enjoy %s style trips. " +
                        "Only recommend a city, not a province or a large region like Gangwon-do. " +
                        "The recommendation should be a city suitable for tourism, not a broad area. " +
                        "Please print only name",
                request.getCompanions(), request.getDepartureCity(),
                request.getStartDate(), request.getEndDate(),
                request.getTransportation(), request.getStyle()
        );
    }

    private String generateInternationalPrompt(InternationalTravelRequest request) {
        return String.format(
                "You are a travel assistant. Recommend a specific city that fits the following conditions, and only return the city name (no other information): " +
                        "\n- Budget: %s USD for the entire trip." +
                        "\n- Traveler: %s-year-old %s traveling with %s." +
                        "\n- Travel dates: From %s to %s." +
                        "\n- Preferences: The traveler prefers %s type of destinations." +
                        "\n- Departure city: %s." +
                        "\nProvide the best possible city destination for this trip, considering the Departure city, budget, flight time and preferences." +
                        "Please print it in Korean",
                request.getBudget(), request.getAge(), request.getGender(), request.getCompanions(),
                request.getStartDate(), request.getEndDate(), request.getPreference(), request.getDepartureCity()
        );
    }
}
