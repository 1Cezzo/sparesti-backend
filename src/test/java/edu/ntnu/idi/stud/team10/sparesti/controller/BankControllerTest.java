package edu.ntnu.idi.stud.team10.sparesti.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

import edu.ntnu.idi.stud.team10.sparesti.dto.AccountDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.TransactionDto;
import edu.ntnu.idi.stud.team10.sparesti.service.BankService;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BankController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BankControllerTest {
  @Autowired MockMvc mockMvc;
  @MockBean BankService bankService;
  private Jwt token;

  @BeforeEach
  void setUp() {
    token = Jwt.withTokenValue("token").claim("userId", 1L).header("alg", "HS256").build();
    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(token, null));
  }

  @Test
  void createAccount() throws Exception {
    AccountDto dto = new AccountDto();
    when(bankService.createAccount(dto)).thenReturn(new AccountDto());
    mockMvc
        .perform(
            put("/api/bank/account/create")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto))
                .secure(true))
        .andExpect(status().isCreated());
  }

  @Test
  void addTransaction() throws Exception {
    TransactionDto transaction = new TransactionDto();
    doNothing().when(bankService).addTransaction(transaction);
    mockMvc
        .perform(
            post("/api/bank/account/transactions")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(transaction))
                .secure(true))
        .andExpect(status().isCreated());
  }

  @Test
  void getAccountDetails() throws Exception {
    AccountDto accountDetails = new AccountDto();
    when(bankService.getAccountDetails(1L, 1L)).thenReturn(accountDetails);
    mockMvc
        .perform(
            get("/api/bank/account/details/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void getAllAccounts() throws Exception {
    Set<AccountDto> accountDetails = new HashSet<>();
    when(bankService.getUserAccounts(1L)).thenReturn(accountDetails);
    mockMvc
        .perform(
            get("/api/bank/account/all")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void transferMoney() throws Exception {
    doNothing().when(bankService).transferMoney(1L, 2L, 100.0, 1L);
    mockMvc
        .perform(
            put("/api/bank/account/transfer")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .param("fromAccountNr", "1")
                .param("toAccountNr", "2")
                .param("amount", "100.0")
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void getAllTransactionsByAccountNr() throws Exception {
    Set<TransactionDto> transactions = new HashSet<>();
    when(bankService.getTransactionsByAccountNr(1L, 1L)).thenReturn(transactions);
    mockMvc
        .perform(
            get("/api/bank/transactions/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void getRecentTransactions() throws Exception {
    Set<TransactionDto> transactions = new HashSet<>();
    when(bankService.getRecentTransactionsByAccountNr(1L, 1L)).thenReturn(transactions);
    mockMvc
        .perform(
            get("/api/bank/transactions/recent/1")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void getRecentCategorySpendings() throws Exception {
    Map<String, Double> spendings = new HashMap<>();
    when(bankService.getSpendingsInCategories(1L, 1L)).thenReturn(spendings);
    mockMvc
        .perform(
            get("/api/bank/transactions/recent/categories/1")
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
