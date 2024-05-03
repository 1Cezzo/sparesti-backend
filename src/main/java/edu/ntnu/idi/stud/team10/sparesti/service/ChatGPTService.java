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

  /**
   * Constructs a ChatGPTService with the necessary RestTemplate.
   *
   * @param restTemplate RestTemplate for sending HTTP requests.
   */
  public ChatGPTService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  /**
   * Sends a series of messages to the ChatGPT API and retrieves the completion.
   *
   * @param messages An array of maps, where each map represents a message with its role and
   *     content.
   * @return The response body from the ChatGPT API as a string.
   * @throws RuntimeException if there is an error converting the request body to JSON or if there
   *     is an error communicating with the ChatGPT API.
   */
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
