// package edu.ntnu.idi.stud.team10.sparesti.controller;
//
// import java.time.Instant;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.Map;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.security.oauth2.jwt.JwtDecoder;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
// import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
// import edu.ntnu.idi.stud.team10.sparesti.service.UserService;
//
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
// @WebMvcTest(UserController.class)
// public class UserControllerTest {
//
//  @Autowired
//  private MockMvc mockMvc;
//
//  @MockBean private UserService userService;
//
//  @Mock private JwtDecoder jwtDecoder;
//
//  @InjectMocks private UserController userController;
//
//  private String accessToken;
//
//  @BeforeEach
//  public void setup() {
//    userController = new UserController(userService);
//    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
//
//    // Generate a sample JWT access token
//    Map<String, Object> claims = new HashMap<>();
//    claims.put("userId", 1L);
//    claims.put("sub", "user@example.com");
//    claims.put("iat", Date.from(Instant.now()));
//    claims.put("exp", Date.from(Instant.now().plusSeconds(3600)));
//
//    accessToken =
//        Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, "secret").compact();
//  }
//
//  @Test
//  public void testGetUserByUsername() throws Exception {
//    UserDto userDto = new UserDto();
//    userDto.setId(1L);
//    userDto.setEmail("user@example.com");
//
//    when(userService.getUserById(any(Long.class))).thenReturn(userDto);
//
//    mockMvc
//        .perform(
//            MockMvcRequestBuilders.get("/api/users")
//                .header("Authorization", "Bearer " + accessToken)
//                .contentType(MediaType.APPLICATION_JSON))
//        .andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
//        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("user@example.com"));
//  }
//
//  @Test
//  public void testCreateUser() throws Exception {
//    UserDto userDto = new UserDto();
//    userDto.setUsername("user@example.com");
//
//    when(userService.addUser(any(UserDto.class))).thenReturn(userDto);
//
//    mockMvc
//        .perform(
//            MockMvcRequestBuilders.post("/api/users/create")
//                .header("Authorization", "Bearer " + accessToken)
//                .content(asJsonString(userDto))
//                .contentType(MediaType.APPLICATION_JSON))
//        .andExpect(status().isCreated())
//        .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("user@example.com"));
//  }
//
//  @Test
//  public void testDeleteUser() throws Exception {
//    mockMvc
//        .perform(
//            MockMvcRequestBuilders.delete("/api/users/delete")
//                .header("Authorization", "Bearer " + accessToken)
//                .contentType(MediaType.APPLICATION_JSON))
//        .andExpect(status().isNoContent());
//  }
//
//  @Test
//  public void testUpdateUser() throws Exception {
//    UserDto userDto = new UserDto();
//    userDto.setId(1L);
//    userDto.setUsername("user@example.com");
//
//    when(userService.updateUser(any(UserDto.class))).thenReturn(userDto);
//
//    mockMvc
//        .perform(
//            MockMvcRequestBuilders.post("/api/users/update")
//                .header("Authorization", "Bearer " + accessToken)
//                .content(asJsonString(userDto))
//                .contentType(MediaType.APPLICATION_JSON))
//        .andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
//        .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("user@example.com"));
//  }
//
//  @Test
//  public void testUpdateLoginStreak() throws Exception {
//    mockMvc
//        .perform(
//            MockMvcRequestBuilders.post("/api/users/update-login-streak")
//                .header("Authorization", "Bearer " + accessToken)
//                .contentType(MediaType.APPLICATION_JSON))
//        .andExpect(status().isOk());
//  }
//
//  @Test
//  public void testGetLoginStreak() throws Exception {
//    when(userService.getLoginStreak(any(Long.class))).thenReturn(5);
//
//    mockMvc
//        .perform(
//            MockMvcRequestBuilders.get("/api/users/login-streak")
//                .header("Authorization", "Bearer " + accessToken)
//                .contentType(MediaType.APPLICATION_JSON))
//        .andExpect(status().isOk())
//        .andExpect(MockMvcResultMatchers.jsonPath("$").value(5));
//  }
//
//  private String asJsonString(Object obj) {
//    try {
//      return new ObjectMapper().writeValueAsString(obj);
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    }
//  }
// }
