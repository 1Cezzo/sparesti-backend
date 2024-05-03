package edu.ntnu.idi.stud.team10.sparesti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.service.UserService;
import edu.ntnu.idi.stud.team10.sparesti.util.TokenParser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/** Controller for handling user requests. */
@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "Operations related to creating and deleting a user")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Get a user by username.
   *
   * @param token the JWT access token.
   * @return the user with the given username
   */
  @GetMapping()
  @Operation(summary = "Access the user data")
  public UserDto getUserByUsername(@AuthenticationPrincipal Jwt token) {
    Long userId = TokenParser.extractUserId(token);
    return userService.getUserById(userId);
  }

  /**
   * Create a new user.
   *
   * @param userDTO the user to create
   * @return the response entity
   */
  @PostMapping("/create")
  @Operation(summary = "Create a new user")
  public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(userDTO));
  }

  /**
   * Delete a user by id.
   *
   * @param token the JWT access token.
   * @return ResponseEntity with status code.
   */
  @DeleteMapping("/delete")
  @Operation(summary = "Delete a user")
  public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal Jwt token) {
    Long id = TokenParser.extractUserId(token);
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Update a user.
   *
   * @param userDTO the user to update
   * @return the response entity
   */
  @PostMapping("/update")
  @Operation(summary = "Update a user")
  public ResponseEntity<UserDto> updateUser(
      @RequestBody UserDto userDTO, @AuthenticationPrincipal Jwt token) {
    Long userId = TokenParser.extractUserId(token);
    userDTO.setId(userId);
    return ResponseEntity.ok(userService.updateUser(userDTO));
  }

  /**
   * Update login streak.
   *
   * @param token the JWT access token.
   */
  @PostMapping("/update-login-streak")
  @Operation(summary = "Update login streak")
  public void updateLoginStreak(@AuthenticationPrincipal Jwt token) {
    Long userId = token.getClaim("userId");
    userService.updateLoginStreak(userId);
  }
}
