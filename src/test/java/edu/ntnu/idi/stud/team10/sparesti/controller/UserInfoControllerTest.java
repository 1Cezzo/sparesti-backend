package edu.ntnu.idi.stud.team10.sparesti.controller;

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

import edu.ntnu.idi.stud.team10.sparesti.dto.UserInfoDto;
import edu.ntnu.idi.stud.team10.sparesti.service.UserInfoService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserInfoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserInfoControllerTest {
  @Autowired MockMvc mockMvc;
  @MockBean UserInfoService userInfoService;
  private Jwt token;

  @BeforeEach
  void setUp() {
    token = Jwt.withTokenValue("token").claim("userId", 1L).header("alg", "HS256").build();
    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(token, null));
  }

  @Test
  void createUserInfo() throws Exception {
    UserInfoDto userInfoDto = new UserInfoDto();
    when(userInfoService.createUserInfo(userInfoDto)).thenReturn(userInfoDto);
    mockMvc
        .perform(
            post("/api/user-info/create")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userInfoDto))
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void updateUserInfo() throws Exception {
    UserInfoDto userInfoDto = new UserInfoDto();
    when(userInfoService.updateUserInfo(1L, userInfoDto)).thenReturn(userInfoDto);
    mockMvc
        .perform(
            post("/api/user-info/update")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userInfoDto))
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
