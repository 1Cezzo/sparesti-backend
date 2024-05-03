package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.ArrayList;
import java.util.HashSet;

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

import edu.ntnu.idi.stud.team10.sparesti.dto.BadgeDto;
import edu.ntnu.idi.stud.team10.sparesti.model.Badge;
import edu.ntnu.idi.stud.team10.sparesti.service.BadgeAwarder;
import edu.ntnu.idi.stud.team10.sparesti.service.BadgeService;
import edu.ntnu.idi.stud.team10.sparesti.service.UserBadgeService;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BadgeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BadgeControllerTest {
  @Autowired MockMvc mockMvc;
  @MockBean BadgeService badgeService;
  @MockBean UserBadgeService userBadgeService;
  @MockBean BadgeAwarder badgeAwarder;
  private Jwt token;

  @BeforeEach
  void setUp() {
    token = Jwt.withTokenValue("token").claim("userId", 1L).header("alg", "HS256").build();
    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(token, null));
  }

  @Test
  void getAllBadges() throws Exception {
    when(badgeService.getAllBadges()).thenReturn(new ArrayList<>());
    mockMvc
        .perform(
            get("/api/badges")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void createBadge() throws Exception {
    BadgeDto dto = new BadgeDto();
    when(badgeService.createBadge(dto)).thenReturn(new BadgeDto());
    mockMvc
        .perform(
            post("/api/badges/create")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto))
                .secure(true))
        .andExpect(status().isCreated());
  }

  @Test
  void getBadgeRarity() throws Exception {
    when(badgeService.findBadgeRarity(1L)).thenReturn(0.5);
    mockMvc
        .perform(
            get("/api/badges/rarity/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void getBadgeInfo() throws Exception {
    when(badgeService.getBadgeById(1L)).thenReturn(new BadgeDto());
    mockMvc
        .perform(
            get("/api/badges/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void getAllBadgesByUserId() throws Exception {
    when(userBadgeService.getAllBadgesByUserId(1L)).thenReturn(new HashSet<>());
    mockMvc
        .perform(
            get("/api/badges/user")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void giveUserBadge() throws Exception {
    doNothing().when(userBadgeService).giveUserBadge(1L, 1L);
    mockMvc
        .perform(
            post("/api/badges/1/give/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void removeUserBadge() throws Exception {
    doNothing().when(userBadgeService).removeUserBadge(1L, 1L);
    mockMvc
        .perform(
            delete("/api/badges/1/remove/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isNoContent());
  }

  @Test
  void getUsersByBadge() throws Exception {
    when(userBadgeService.getUsersByBadge(1L)).thenReturn(new ArrayList<>());
    mockMvc
        .perform(
            get("/api/badges/badge/1/users")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void awardUserBadge() throws Exception {
    when(badgeAwarder.checkAndAwardBadges(1L)).thenReturn(new Badge());
    mockMvc
        .perform(
            post("/api/badges/check-and-award")
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
