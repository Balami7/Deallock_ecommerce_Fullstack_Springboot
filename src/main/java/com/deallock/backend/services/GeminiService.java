package com.deallock.backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getResponse(String userMessage) {

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + apiKey;

        // Add context about your app (VERY IMPORTANT)
        String prompt = """
                You are a helpful assistant for an ecommerce platform called DealLock.
                Help users with:
                - creating deals
                - payments
                - tracking deals
                - account issues
                
                User: """ + userMessage;

        // Request body
        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt)
                        ))
                )
        );

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    url,
                    request,
                    Map.class
            );

            return extractText(response.getBody());

        } catch (Exception e) {
            return "Sorry, I couldn't process your request right now.";
        }
    }

    // Extract only the chatbot reply from Gemini response
    private String extractText(Map body) {
        try {
            List candidates = (List) body.get("candidates");
            Map firstCandidate = (Map) candidates.get(0);

            Map content = (Map) firstCandidate.get("content");
            List parts = (List) content.get("parts");

            Map firstPart = (Map) parts.get(0);

            return (String) firstPart.get("text");

        } catch (Exception e) {
            return "Sorry, I couldn't understand the response.";
        }
    }
}
