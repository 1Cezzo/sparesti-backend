package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ntnu.idi.stud.team10.sparesti.dto.ChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.ConsumptionChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.PurchaseChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.SavingChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserInfoDto;
import edu.ntnu.idi.stud.team10.sparesti.service.ChallengeService;
import edu.ntnu.idi.stud.team10.sparesti.service.UserChallengeService;
import edu.ntnu.idi.stud.team10.sparesti.service.UserInfoService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChallengeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ChallengeControllerTest {
  @Autowired MockMvc mockMvc;
  @MockBean ChallengeService challengeService;
  @MockBean UserChallengeService userChallengeService;
  @MockBean UserInfoService userInfoService;
  private Jwt token;

  @BeforeEach
  void setUp() {
    token = Jwt.withTokenValue("token").claim("userId", 1L).header("alg", "HS256").build();
    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(token, null));
  }

  @Test
  void addChallengeToUser() throws Exception {
    UserDto userDto = new UserDto();
    when(userChallengeService.addChallengeToUser(1L, 1L)).thenReturn(userDto);
    mockMvc
        .perform(
            post("/api/challenges/users/challenges/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void removeChallengeFromUser() throws Exception {
    UserDto userDto = new UserDto();
    when(userChallengeService.removeChallengeFromUser(1L, 1L)).thenReturn(userDto);
    mockMvc
        .perform(
            delete("/api/challenges/users/challenges/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void fetchConsumptionChallengesForUser() throws Exception {
    List<ConsumptionChallengeDto> consumptionChallenges = new ArrayList<>();
    when(userChallengeService.fetchConsumptionChallengesForUser(1L))
        .thenReturn(consumptionChallenges);
    mockMvc
        .perform(
            get("/api/challenges/users/consumption-challenges")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void fetchPurchaseChallengesForUser() throws Exception {
    List<PurchaseChallengeDto> purchaseChallenges = new ArrayList<>();
    when(userChallengeService.fetchPurchaseChallengesForUser(1L)).thenReturn(purchaseChallenges);
    mockMvc
        .perform(
            get("/api/challenges/users/purchase-challenges")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void fetchSavingChallengesForUser() throws Exception {
    List<SavingChallengeDto> savingChallenges = new ArrayList<>();
    when(userChallengeService.fetchSavingChallengesForUser(1L)).thenReturn(savingChallenges);
    mockMvc
        .perform(
            get("/api/challenges/users/saving-challenges")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void getChallengesByUser() throws Exception {
    List<ChallengeDto> challenges = new ArrayList<>();
    when(userChallengeService.getSortedChallengesByUser(1L)).thenReturn(challenges);
    mockMvc
        .perform(
            get("/api/challenges/users/challenges")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void getActiveChallengesByUser() throws Exception {
    List<ChallengeDto> challenges = new ArrayList<>();
    when(userChallengeService.getActiveSortedChallengesByUser(1L)).thenReturn(challenges);
    mockMvc
        .perform(
            get("/api/challenges/users/active-challenges")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void suggestChallenge() throws Exception {
    ChallengeDto challengeDto = new ChallengeDto();
    when(userChallengeService.generateChatResponse(any(UserInfoDto.class)))
        .thenReturn(challengeDto);
    mockMvc
        .perform(
            get("/api/challenges/suggest-ai-challenge")
                .param("userEmail", "test@example.com")
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void suggestRandomChallenge() throws Exception {
    ChallengeDto challengeDto = new ChallengeDto();
    when(userChallengeService.generateRandomChallenge(any(UserInfoDto.class)))
        .thenReturn(challengeDto);
    mockMvc
        .perform(
            get("/api/challenges/suggest-random-challenge")
                .param("userEmail", "test@example.com")
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void updateCompletedValueOfChallenge() throws Exception {
    when(userChallengeService.updateCompleted(1L, 1L)).thenReturn(true);
    mockMvc
        .perform(
            put("/api/challenges/users/challenges/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
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
