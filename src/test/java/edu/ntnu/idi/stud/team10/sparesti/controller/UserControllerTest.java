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

import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.service.UserService;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
  @Autowired MockMvc mockMvc;
  @MockBean UserService userService;
  private Jwt token;

  @BeforeEach
  void setUp() {
    token = Jwt.withTokenValue("token").claim("userId", 1L).header("alg", "HS256").build();
    SecurityContextHolder.getContext()
        .setAuthentication(new UsernamePasswordAuthenticationToken(token, null));
  }

  @Test
  void getUserByUsername() throws Exception {
    UserDto userDto = new UserDto();
    when(userService.getUserById(1L)).thenReturn(userDto);
    mockMvc
        .perform(
            get("/api/users")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void createUser() throws Exception {
    UserDto userDto = new UserDto();
    when(userService.addUser(userDto)).thenReturn(userDto);
    mockMvc
        .perform(
            post("/api/users/create")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDto))
                .secure(true))
        .andExpect(status().isCreated());
  }

  @Test
  void deleteUser() throws Exception {
    doNothing().when(userService).deleteUser(1L);
    mockMvc
        .perform(
            delete("/api/users/delete")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isNoContent());
  }

  @Test
  void updateUser() throws Exception {
    UserDto userDto = new UserDto();
    when(userService.updateUser(userDto)).thenReturn(userDto);
    mockMvc
        .perform(
            post("/api/users/update")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDto))
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void updateLoginStreak() throws Exception {
    doNothing().when(userService).updateLoginStreak(1L);
    mockMvc
        .perform(
            post("/api/users/update-login-streak")
                .header("Authorization", "Bearer" + token.getTokenValue())
                .secure(true))
        .andExpect(status().isOk());
  }

  @Test
  void getLoginStreak() throws Exception {
    when(userService.getLoginStreak(1L)).thenReturn(1);
    mockMvc
        .perform(
            get("/api/users/login-streak")
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
