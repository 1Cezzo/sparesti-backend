package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetRowDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.TransactionDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.BudgetRowMapper;
import edu.ntnu.idi.stud.team10.sparesti.mapper.TransactionMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;
import edu.ntnu.idi.stud.team10.sparesti.model.Transaction;
import edu.ntnu.idi.stud.team10.sparesti.repository.BudgetRowRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.bank.TransactionRepository;
import edu.ntnu.idi.stud.team10.sparesti.service.BudgetRowService;
import edu.ntnu.idi.stud.team10.sparesti.service.TransactionBudgetRowService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionBudgetRowController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TransactionBudgetRowControllerTest {
  @Autowired MockMvc mockMvc;
  @MockBean TransactionBudgetRowService transactionBudgetRowService;
  @MockBean BudgetRowMapper budgetRowMapper;
  @MockBean TransactionMapper transactionMapper;
  @MockBean BudgetRowRepository budgetRowRepository;
  @MockBean TransactionRepository transactionRepository;
  @MockBean BudgetRowService budgetRowService;
  static MockedStatic<BudgetRowService> mockedBudgetRowService;
  private Jwt token;

  @BeforeEach
  void setUp() {
    token = Jwt.withTokenValue("token").claim("userId", 1L).header("alg", "HS256").build();
    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(token, null));
  }

  @BeforeAll
  static void setUpAll() {
    mockedBudgetRowService = Mockito.mockStatic(BudgetRowService.class);
    mockedBudgetRowService
        .when(() -> BudgetRowService.updateBudgetRow(any(Long.class), any(BudgetRowDto.class)))
        .thenReturn(new BudgetRow());
  }

  @Test
  void addTransactionToBudgetRow() throws Exception {
    when(transactionRepository.findById(1L)).thenReturn(java.util.Optional.of(new Transaction()));
    when(budgetRowRepository.findById(1L)).thenReturn(java.util.Optional.of(new BudgetRow()));
    when(transactionMapper.toDto(any(Transaction.class))).thenReturn(new TransactionDto());
    when(budgetRowMapper.toDto(any(BudgetRow.class))).thenReturn(new BudgetRowDto());
    BudgetRow entity = new BudgetRow();
    entity.setUsedAmount(5.0);
    when(budgetRowMapper.toEntity(any(BudgetRowDto.class))).thenReturn(entity);
    mockMvc
        .perform(
            post("/api/transaction-budget-row/add/1/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void getTransactionsNotInBudgetRow() throws Exception {
    Set<Transaction> transactionDtos = new HashSet<>();
    when(transactionBudgetRowService.getTransactionsNotInBudgetRow(1L)).thenReturn(transactionDtos);
    mockMvc
        .perform(
            get("/api/transaction-budget-row/transactions-not-in-budget-row")
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
