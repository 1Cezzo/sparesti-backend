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

import edu.ntnu.idi.stud.team10.sparesti.dto.PurchaseChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.ChallengeMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.PurchaseChallenge;
import edu.ntnu.idi.stud.team10.sparesti.service.PurchaseChallengeService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PurchaseChallengeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PurchaseChallengeControllerTest {
  @Autowired MockMvc mockMvc;
  @MockBean PurchaseChallengeService purchaseChallengeService;
  @MockBean ChallengeMapper challengeMapper;
  private Jwt token;

  @BeforeEach
  void setUp() {
    token = Jwt.withTokenValue("token").claim("userId", 1L).header("alg", "HS256").build();
    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(token, null));
  }

  @Test
  void createPurchaseChallenge() throws Exception {
    PurchaseChallengeDto dto = new PurchaseChallengeDto();
    when(purchaseChallengeService.createChallenge(any(PurchaseChallenge.class)))
        .thenReturn(new PurchaseChallenge());
    mockMvc
        .perform(
            post("/api/purchase-challenges/purchase")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto))
                .secure(true))
        .andExpect(status().isCreated());
  }

  @Test
  void updatePurchaseChallenge() throws Exception {
    PurchaseChallengeDto dto = new PurchaseChallengeDto();
    when(purchaseChallengeService.updatePurchaseChallenge(1L, dto))
        .thenReturn(new PurchaseChallenge());
    mockMvc
        .perform(
            put("/api/purchase-challenges/purchase/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto))
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void deletePurchaseChallenge() throws Exception {
    doNothing().when(purchaseChallengeService).deletePurchaseChallenge(1L);
    mockMvc
        .perform(
            delete("/api/purchase-challenges/purchase/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isNoContent());
  }

  @Test
  void getAllPurchaseChallenges() throws Exception {
    List<PurchaseChallenge> challenges = new ArrayList<>();
    when(purchaseChallengeService.getAllPurchaseChallenges()).thenReturn(challenges);
    mockMvc
        .perform(
            get("/api/purchase-challenges/purchase")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void getPurchaseChallengeById() throws Exception {
    Optional<PurchaseChallenge> challenge = Optional.of(new PurchaseChallenge());
    when(purchaseChallengeService.getPurchaseChallengeById(1L)).thenReturn(challenge);
    mockMvc
        .perform(
            get("/api/purchase-challenges/purchase/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void addAmountToPurchaseChallenge() throws Exception {
    doNothing().when(purchaseChallengeService).addToSavedAmount(1L, 100.0);
    mockMvc
        .perform(
            put("/api/purchase-challenges/purchase/1/add")
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
