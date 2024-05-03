package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.ArrayList;
import java.util.List;

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

import edu.ntnu.idi.stud.team10.sparesti.dto.SavingsGoalDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserSavingsGoalDto;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;
import edu.ntnu.idi.stud.team10.sparesti.service.SavingsGoalService;
import edu.ntnu.idi.stud.team10.sparesti.service.UserService;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SavingsGoalController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SavingsGoalControllerTest {
  @Autowired MockMvc mockMvc;
  @MockBean SavingsGoalService savingsGoalService;
  @MockBean UserService userService;
  private Jwt token;

  @BeforeEach
  void setUp() {
    token = Jwt.withTokenValue("token").claim("userId", 1L).header("alg", "HS256").build();
    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(token, null));
  }

  @Test
  void createSavingsGoal() throws Exception {
    SavingsGoalDto dto = new SavingsGoalDto();
    when(savingsGoalService.createSavingsGoal(dto, 1L)).thenReturn(new SavingsGoal());
    mockMvc
        .perform(
            post("/api/savings-goals")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto))
                .secure(true))
        .andExpect(status().isCreated());
  }

  @Test
  void getCurrentSavingsGoal() throws Exception {
    SavingsGoalDto dto = new SavingsGoalDto();
    when(savingsGoalService.getCurrentSavingsGoal(1L)).thenReturn(dto);
    mockMvc
        .perform(
            get("/api/savings-goals/current")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void getSavingsGoalById() throws Exception {
    SavingsGoal savingsGoal = new SavingsGoal();
    when(savingsGoalService.getSavingsGoalById(1L)).thenReturn(savingsGoal);
    mockMvc
        .perform(
            get("/api/savings-goals/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void updateSavingsGoal() throws Exception {
    SavingsGoalDto dto = new SavingsGoalDto();
    when(savingsGoalService.updateSavingsGoal(1L, dto)).thenReturn(new SavingsGoal());
    mockMvc
        .perform(
            put("/api/savings-goals/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto))
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void deleteSavingsGoalById() throws Exception {
    doNothing().when(savingsGoalService).deleteSavingsGoalById(1L);
    mockMvc
        .perform(
            delete("/api/savings-goals/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isNoContent());
  }

  @Test
  void updateSavedAmount() throws Exception {
    doNothing().when(savingsGoalService).updateSavedAmount(1L, 1L, 100.0);
    mockMvc
        .perform(
            put("/api/savings-goals/user/saving_goal/1/update-saved-amount")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .param("savedAmount", "100.0")
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void addSavingsGoalToUser() throws Exception {
    UserDto userDto = new UserDto();
    userDto.setId(1L);
    when(userService.getUserByEmail("test@example.com")).thenReturn(userDto);
    doNothing().when(savingsGoalService).addSavingsGoalToUser(1L, 1L);
    mockMvc
        .perform(
            post("/api/savings-goals/add-user/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .param("userEmail", "test@example.com")
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void getAllSavingsGoalsForUser() throws Exception {
    List<SavingsGoalDto> savingsGoalDtos = new ArrayList<>();
    when(savingsGoalService.getAllSavingsGoalsForUser(1L)).thenReturn(savingsGoalDtos);
    mockMvc
        .perform(
            get("/api/savings-goals/savings-goals")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void deleteSavingsGoalFromUser() throws Exception {
    UserDto userDto = new UserDto();
    userDto.setId(1L);
    when(userService.getUserByEmail("test@example.com")).thenReturn(userDto);
    doNothing().when(savingsGoalService).deleteSavingsGoalFromUser(1L, 1L);
    mockMvc
        .perform(
            delete("/api/savings-goals/1/user/delete")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .param("userEmail", "test@example.com")
                .secure(true))
        .andExpect(status().isNoContent());
  }

  @Test
  void getUsersBySavingsGoal() throws Exception {
    List<UserSavingsGoalDto> userSavingsGoalDtos = new ArrayList<>();
    when(savingsGoalService.getUsersBySavingsGoal(1L)).thenReturn(userSavingsGoalDtos);
    mockMvc
        .perform(
            get("/api/savings-goals/1/users")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void hasActiveSavingsGoal() throws Exception {
    UserDto userDto = new UserDto();
    userDto.setId(1L);
    when(userService.getUserByEmail("test@example.com")).thenReturn(userDto);
    when(savingsGoalService.hasActiveSavingsGoal(1L)).thenReturn(true);
    mockMvc
        .perform(
            get("/api/savings-goals/user/test@example.com/active")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void markSavingsGoalAsCompleted() throws Exception {
    doNothing().when(savingsGoalService).completeCurrentSavingsGoal(1L);
    mockMvc
        .perform(
            patch("/api/savings-goals/complete")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  /**
   * Converts object to json string
   *
   * @param obj object to convert
   * @return json string
   */
  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
