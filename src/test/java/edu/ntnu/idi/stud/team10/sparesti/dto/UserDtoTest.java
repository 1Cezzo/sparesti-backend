package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.model.User;

import static org.junit.jupiter.api.Assertions.*;

public class UserDtoTest {

  private UserDto userDto;
  private UserDto userDto2;
  private User user;
  private final Long testId = 1L;
  private final String testUsername = "testuser";
  private final String testPassword = "testpassword";
  private final String testEmail = "test@example.com";
  private final String testProfilePictureUrl = "http://example.com/image.jpg";
  private List<SavingsGoalDTO> testSavingsGoals;
  private List<ChallengeDTO> testChallenges;

  @BeforeEach
  public void setUp() {
    userDto = new UserDto();
    userDto.setId(testId);
    userDto.setDisplayName(testUsername);
    userDto.setPassword(testPassword);
    userDto.setEmail(testEmail);
    userDto.setProfilePictureUrl(testProfilePictureUrl);
    testSavingsGoals = new ArrayList<>();
    testChallenges = new ArrayList<>();
    // Add test savings goals and challenges as needed
    userDto.setSavingsGoals(testSavingsGoals);
    userDto.setChallenges(testChallenges);
    userDto2 = new UserDto();
    userDto2.setId(testId);
    userDto2.setDisplayName(testUsername);
    userDto2.setPassword(testPassword);
    userDto2.setEmail(testEmail);
    userDto2.setProfilePictureUrl(testProfilePictureUrl);
    userDto2.setSavingsGoals(testSavingsGoals);
    userDto2.setChallenges(testChallenges);
    userDto2.setSavingsGoals(testSavingsGoals);
    userDto2.setChallenges(testChallenges);

    user = new User();
    user.setId(1L);
    user.setEmail("test@example.com");
  }

  @Test
  public void testUserDtoAttributes() {
    assertNotNull(userDto);
    assertEquals(testId, userDto.getId());
    assertEquals(testUsername, userDto.getDisplayName());
    assertEquals(testPassword, userDto.getPassword());
    assertEquals(testEmail, userDto.getEmail());
    assertEquals(testProfilePictureUrl, userDto.getProfilePictureUrl());
    assertEquals(testSavingsGoals, userDto.getSavingsGoals());
    assertEquals(testChallenges, userDto.getChallenges());
  }

  @Test
  public void testEquals() {

    // Test with equal objects
    assertTrue(userDto.equals(userDto2));
    assertTrue(userDto2.equals(userDto));

    // Test with the same object
    assertTrue(userDto.equals(userDto));

    // Test with null object
    assertFalse(userDto.equals(null));

    // Test with different id
    userDto2.setId(2L);
    assertFalse(userDto.equals(userDto2));

    // Test with different username
    userDto2.setId(1L);
    userDto2.setDisplayName("differentuser");
    assertFalse(userDto.equals(userDto2));

    // Test with different email
    userDto2.setDisplayName("testuser");
    userDto2.setEmail("different@example.com");
    assertFalse(userDto.equals(userDto2));

    // Test with different object type
    assertFalse(userDto.equals("test"));
  }

  @Test
  public void testHashCode() {
    assertEquals(userDto.hashCode(), userDto2.hashCode());
  }

  @Test
  public void testToEntity() {
    User convertedUser = userDto.toEntity();
    assertNotNull(convertedUser);
    assertEquals(user.getId(), convertedUser.getId());
    assertEquals(user.getEmail(), convertedUser.getEmail());
  }

  @Test
  public void testUserDtoConstructor() {
    UserDto mappedUserDto = new UserDto(user);
    assertNotNull(mappedUserDto);
    assertEquals(user.getId(), mappedUserDto.getId());
    assertEquals(user.getEmail(), mappedUserDto.getEmail());
  }

  // Add more tests as needed...

}
