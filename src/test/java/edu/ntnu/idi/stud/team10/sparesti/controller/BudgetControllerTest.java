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

import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetRowDto;
import edu.ntnu.idi.stud.team10.sparesti.service.UserBudgetService;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BudgetController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BudgetControllerTest {
  @Autowired MockMvc mockMvc;
  @MockBean UserBudgetService userBudgetService;
  private Jwt token;

  @BeforeEach
  void setUp() {
    token = Jwt.withTokenValue("token").claim("userId", 1L).header("alg", "HS256").build();
    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(token, null));
  }

  @Test
  void addBudgetToUser() throws Exception {
    BudgetDto dto = new BudgetDto();
    when(userBudgetService.addBudgetToUser(1L, dto)).thenReturn(dto);
    mockMvc
        .perform(
            post("/api/budget/budgets/add")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"name\":\"test\",\"description\":\"test\",\"budgetRows\":[]}")
                .secure(true))
        .andExpect(status().isCreated());
  }

  @Test
  void getAllBudgetsForUser() throws Exception {
    List<BudgetDto> budgetDtos = new ArrayList<>();
    when(userBudgetService.getAllBudgetsForUser(1L)).thenReturn(budgetDtos);
    mockMvc
        .perform(
            get("/api/budget/budgets")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void getBudgetForUser() throws Exception {
    BudgetDto budgetDto = new BudgetDto();
    when(userBudgetService.getBudgetForUser(1L, 1L)).thenReturn(budgetDto);
    mockMvc
        .perform(
            get("/api/budget/budgets/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void deleteBudgetFromUser() throws Exception {
    doNothing().when(userBudgetService).deleteBudgetFromUser(1L, 1L);
    mockMvc
        .perform(
            delete("/api/budget/budgets/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isNoContent());
  }

  @Test
  void addBudgetRowToUserBudget() throws Exception {
    BudgetRowDto budgetRowDto = new BudgetRowDto();
    when(userBudgetService.addBudgetRowToUserBudget(1L, 1L, budgetRowDto))
        .thenReturn(new BudgetDto());
    mockMvc
        .perform(
            post("/api/budget/budgets/1/rows/add")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(budgetRowDto))
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void deleteBudgetRowFromUserBudget() throws Exception {
    doNothing().when(userBudgetService).deleteBudgetRowFromUserBudget(1L, 1L, 1L);
    mockMvc
        .perform(
            delete("/api/budget/budgets/1/rows/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isNoContent());
  }

  @Test
  void editBudgetRowInUserBudget() throws Exception {
    BudgetRowDto budgetRowDto = new BudgetRowDto();
    when(userBudgetService.editBudgetRowInUserBudget(1L, 1L, 1L, budgetRowDto))
        .thenReturn(budgetRowDto);
    mockMvc
        .perform(
            put("/api/budget/budgets/1/rows/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(budgetRowDto))
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void getNewestBudget() throws Exception {
    Optional<BudgetDto> budgetDto = Optional.of(new BudgetDto());
    when(userBudgetService.getNewestBudget(1L)).thenReturn(budgetDto);
    mockMvc
        .perform(
            get("/api/budget/budgets/getnew")
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
