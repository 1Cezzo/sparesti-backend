package edu.ntnu.idi.stud.team10.sparesti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ntnu.idi.stud.team10.sparesti.dto.UserInfoDto;
import edu.ntnu.idi.stud.team10.sparesti.service.UserInfoService;
import edu.ntnu.idi.stud.team10.sparesti.util.TokenParser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user-info")
@Tag(name = "User Details", description = "Operations related to user info from questionnaire.")
/** Controller class for the UserInfo entity. */
public class UserInfoController {
  private UserInfoService userInfoService;

  /**
   * Constructor for UserInfoController.
   *
   * @param userInfoService The user info service
   */
  @Autowired
  public UserInfoController(UserInfoService userInfoService) {
    this.userInfoService = userInfoService;
  }

  /**
   * Create user info.
   *
   * @param token The JWT access token
   * @param userInfoDto The user info DTO
   * @return The user info DTO
   */
  @PostMapping("/create")
  @Operation(summary = "Create user info")
  public ResponseEntity<UserInfoDto> createUserInfo(
      @AuthenticationPrincipal Jwt token, @RequestBody UserInfoDto userInfoDto) {
    userInfoDto.setUserId(TokenParser.extractUserId(token));
    return ResponseEntity.ok(userInfoService.createUserInfo(userInfoDto));
  }

  /**
   * Update user info.
   *
   * @param token The JWT access token
   * @param userInfoDto The user info DTO
   * @return The user info DTO
   */
  @PostMapping("/update")
  @Operation(summary = "Update user info")
  public ResponseEntity<UserInfoDto> updateUserInfo(
      @AuthenticationPrincipal Jwt token, @RequestBody UserInfoDto userInfoDto) {
    Long userId = TokenParser.extractUserId(token);
    return ResponseEntity.ok(userInfoService.updateUserInfo(userId, userInfoDto));
  }
}
