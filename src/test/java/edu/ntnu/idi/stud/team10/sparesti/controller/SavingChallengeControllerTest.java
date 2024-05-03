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

import edu.ntnu.idi.stud.team10.sparesti.dto.SavingChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.ChallengeMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingChallenge;
import edu.ntnu.idi.stud.team10.sparesti.service.SavingChallengeService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SavingChallengeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SavingChallengeControllerTest {
  @Autowired MockMvc mockMvc;
  @MockBean SavingChallengeService savingChallengeService;
  @MockBean ChallengeMapper challengeMapper;
  private Jwt token;

  @BeforeEach
  void setUp() {
    token = Jwt.withTokenValue("token").claim("userId", 1L).header("alg", "HS256").build();
    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(token, null));
  }

  @Test
  void createSavingChallenge() throws Exception {
    SavingChallengeDto dto = new SavingChallengeDto();
    when(savingChallengeService.createChallenge(any(SavingChallenge.class)))
        .thenReturn(new SavingChallenge());
    mockMvc
        .perform(
            post("/api/saving-challenges/saving")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto))
                .secure(true))
        .andExpect(status().isCreated());
  }

  @Test
  void updateSavingChallenge() throws Exception {
    SavingChallengeDto dto = new SavingChallengeDto();
    when(savingChallengeService.updateSavingChallenge(1L, dto)).thenReturn(new SavingChallenge());
    mockMvc
        .perform(
            put("/api/saving-challenges/saving/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto))
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void deleteSavingChallenge() throws Exception {
    doNothing().when(savingChallengeService).deleteSavingChallenge(1L);
    mockMvc
        .perform(
            delete("/api/saving-challenges/saving/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isNoContent());
  }

  @Test
  void getAllSavingChallenges() throws Exception {
    List<SavingChallenge> challenges = new ArrayList<>();
    when(savingChallengeService.getAllSavingChallenges()).thenReturn(challenges);
    mockMvc
        .perform(
            get("/api/saving-challenges/saving")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void getSavingChallengeById() throws Exception {
    Optional<SavingChallenge> challenge = Optional.of(new SavingChallenge());
    when(savingChallengeService.getSavingChallengeById(1L)).thenReturn(challenge);
    mockMvc
        .perform(
            get("/api/saving-challenges/saving/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void addAmountToSavingChallenge() throws Exception {
    doNothing().when(savingChallengeService).addToSavedAmount(1L, 100.0);
    mockMvc
        .perform(
            put("/api/saving-challenges/saving/1/add")
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
