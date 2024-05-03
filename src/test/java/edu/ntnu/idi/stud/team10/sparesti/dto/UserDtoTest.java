package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.model.User;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

  private UserDto userDto;
  private UserDto userDto2;
  private User user;
  private final Long testId = 1L;
  private final String testUsername = "testuser";
  private final String testPassword = "testpassword";
  private final String testEmail = "test@example.com";
  private final String testProfilePictureUrl = "http://example.com/image.jpg";
  private List<ChallengeDto> testChallenges;

  @BeforeEach
  public void setUp() {
    userDto = new UserDto();
    userDto.setId(testId);
    userDto.setPassword(testPassword);
    userDto.setEmail(testEmail);
    userDto.setProfilePictureUrl(testProfilePictureUrl);
    testChallenges = new ArrayList<>();

    userDto2 = new UserDto();
    userDto2.setId(testId);
    userDto2.setPassword(testPassword);
    userDto2.setEmail(testEmail);
    userDto2.setProfilePictureUrl(testProfilePictureUrl);

    user = new User();
    user.setId(1L);
    user.setEmail("test@example.com");
  }

  @Test
  void noArgsConstructor() {
    UserDto userDto = new UserDto();
    assertNotNull(userDto);
  }

  @Test
  void allArgsConstructor() {
    UserDto userDto =
        new UserDto(
            testId, testPassword, testEmail, testProfilePictureUrl, 0, null, 0L, 0L, 0.0, null);
    assertNotNull(userDto);
  }

  @Test
  void testGettersAndSetters() {
    UserDto userDto = new UserDto();
    userDto.setId(testId);
    userDto.setPassword(testPassword);
    userDto.setEmail(testEmail);
    userDto.setProfilePictureUrl(testProfilePictureUrl);
    userDto.setLoginStreak(0);
    userDto.setLastLogin(null);
    userDto.setCheckingAccountNr(0L);
    userDto.setSavingsAccountNr(0L);
    userDto.setTotalSavings(0.0);
    userDto.setChallenges(testChallenges);

    assertNotNull(userDto);
    assertEquals(testId, userDto.getId());
    assertEquals(testPassword, userDto.getPassword());
    assertEquals(testEmail, userDto.getEmail());
    assertEquals(testProfilePictureUrl, userDto.getProfilePictureUrl());
    assertEquals(0, userDto.getLoginStreak());
    assertNull(userDto.getLastLogin());
    assertEquals(0L, userDto.getCheckingAccountNr());
    assertEquals(0L, userDto.getSavingsAccountNr());
    assertEquals(0.0, userDto.getTotalSavings());
    assertEquals(testChallenges, userDto.getChallenges());
  }

  @Test
  void testEquals() {

    // Test with equal objects
    assertEquals(userDto, userDto2);
    assertEquals(userDto2, userDto);

    // Test with the same object
    assertEquals(userDto, userDto);

    // Test with null object
    assertNotEquals(null, userDto);

    // Test with different id
    userDto2.setId(2L);
    assertNotEquals(userDto, userDto2);

    // Test with different email
    userDto2.setEmail("different@example.com");
    assertNotEquals(userDto, userDto2);
  }

  @Test
  void testHashCode() {
    assertEquals(userDto.hashCode(), userDto2.hashCode());
  }

  @Test
  void testToString() {
    assertNotNull(userDto.toString());
  }
}
