package edu.ntnu.idi.stud.team10.sparesti.controller;

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

import edu.ntnu.idi.stud.team10.sparesti.service.MockDataService;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MockDataController.class)
@AutoConfigureMockMvc(addFilters = false)
class MockDataControllerTest {
  @Autowired MockMvc mockMvc;
  @MockBean MockDataService mockDataService;
  private Jwt token;

  @BeforeEach
  void setUp() {
    token = Jwt.withTokenValue("token").claim("userId", 1L).header("alg", "HS256").build();
    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(token, null));
  }

  @Test
  void generateMockTransactions() throws Exception {
    doNothing().when(mockDataService).storeRandomMockTransactions(1L, 10);
    mockMvc
        .perform(
            put("/api/mock/transactions/generate")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .param("accountNr", "1")
                .param("count", "10")
                .secure(true))
        .andExpect(status().isOk());
  }
}
