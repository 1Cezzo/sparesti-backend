package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ntnu.idi.stud.team10.sparesti.dto.ConsumptionChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.ChallengeMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.ConsumptionChallenge;
import edu.ntnu.idi.stud.team10.sparesti.service.ConsumptionChallengeService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConsumptionChallengeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ConsumptionChallengeControllerTest {
  @Autowired MockMvc mockMvc;
  @MockBean ConsumptionChallengeService consumptionChallengeService;
  @MockBean ChallengeMapper challengeMapper;
  private Jwt token;

  @BeforeEach
  void setUp() {
    token = Jwt.withTokenValue("token").claim("userId", 1L).header("alg", "HS256").build();
    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(token, null));
  }

  @Test
  void createConsumptionChallenge() throws Exception {
    ConsumptionChallengeDto dto = new ConsumptionChallengeDto();
    when(consumptionChallengeService.createChallenge(any(ConsumptionChallenge.class)))
        .thenReturn(new ConsumptionChallenge());
    mockMvc
        .perform(
            post("/api/consumption-challenges/consumption")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto))
                .secure(true))
        .andExpect(status().isCreated());
  }

  @Test
  void updateConsumptionChallenge() throws Exception {
    ConsumptionChallengeDto dto = new ConsumptionChallengeDto();
    when(consumptionChallengeService.updateConsumptionChallenge(1L, dto))
        .thenReturn(new ConsumptionChallenge());
    mockMvc
        .perform(
            put("/api/consumption-challenges/consumption/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto))
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void deleteConsumptionChallenge() throws Exception {
    doNothing().when(consumptionChallengeService).deleteConsumptionChallenge(1L);
    mockMvc
        .perform(
            delete("/api/consumption-challenges/consumption/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isNoContent());
  }

  @Test
  void getAllConsumptionChallenges() throws Exception {
    List<ConsumptionChallenge> challenges = new ArrayList<>();
    when(consumptionChallengeService.getAllConsumptionChallenges()).thenReturn(challenges);
    mockMvc
        .perform(
            get("/api/consumption-challenges/consumption")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void getConsumptionChallengeById() throws Exception {
    Optional<ConsumptionChallenge> challenge = Optional.of(new ConsumptionChallenge());
    when(consumptionChallengeService.getConsumptionChallengeById(1L)).thenReturn(challenge);
    mockMvc
        .perform(
            get("/api/consumption-challenges/consumption/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void addAmountToConsumptionChallenge() throws Exception {
    doNothing().when(consumptionChallengeService).addToSavedAmount(1L, 100.0);
    mockMvc
        .perform(
            put("/api/consumption-challenges/consumption/1/add")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .param("amount", "100.0")
                .secure(true))
        .andExpect(status().isOk());
  }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
