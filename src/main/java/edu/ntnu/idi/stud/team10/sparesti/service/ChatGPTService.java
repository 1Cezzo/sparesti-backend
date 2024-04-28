package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Service
public class ChatGPTService {
  @Autowired private Environment env;

  private String apiKey;
  private String apiUrl;

  /** Initializes the ChatGPTService with the necessary environment variables. */
  @PostConstruct
  private void init() {
    apiKey = env.getProperty("CHAT_API_KEY");
    apiUrl = env.getProperty("CHAT_API_URL");
  }

  private final RestTemplate restTemplate;

  public ChatGPTService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public String sendMessagesAndGetCompletion(Map<String, String>[] messages) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + apiKey);
    headers.setContentType(MediaType.APPLICATION_JSON);

    // Construct the request body including the messages
    Map<String, Object> requestBodyMap = new HashMap<>();
    requestBodyMap.put("messages", Arrays.asList(messages));
    requestBodyMap.put("model", "gpt-3.5-turbo");
    requestBodyMap.put("max_tokens", 150);
    requestBodyMap.put("temperature", 0.3);

    ObjectMapper objectMapper = new ObjectMapper();
    String requestBody;
    try {
      requestBody = objectMapper.writeValueAsString(requestBodyMap);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error converting request body to JSON");
    }

    HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

    // Send POST request to ChatGPT API
    ResponseEntity<String> response =
        restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);

    if (response.getStatusCode() == HttpStatus.OK) {
      return response.getBody();
    } else {
      throw new RuntimeException("Error communicating with ChatGPT API");
    }
  }
}
