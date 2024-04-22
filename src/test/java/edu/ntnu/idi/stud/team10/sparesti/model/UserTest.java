package edu.ntnu.idi.stud.team10.sparesti.model;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest {

  private User user;

  @BeforeEach
  public void setUp() {
    user = new User();
    user.setId(1L);
    user.setUsername("testUser");
    user.setPassword("testPassword");
    user.setEmail("test@example.com");
    user.setProfilePictureUrl("https://example.com/profile.jpg");
    user.setCheckingAccountNr(123456);
    user.setSavingsAccountNr(789012);
    user.setSavingsGoals(new ArrayList<>());
    user.setChallenges(new ArrayList<>());
    user.setEarnedBadges(new HashSet<>());
  }

  @Test
  public void testUserFields() {
    assertEquals(1L, user.getId());
    assertEquals("testUser", user.getUsername());
    assertEquals("testPassword", user.getPassword());
    assertEquals("test@example.com", user.getEmail());
    assertEquals("https://example.com/profile.jpg", user.getProfilePictureUrl());
    assertEquals(123456, user.getCheckingAccountNr());
    assertEquals(789012, user.getSavingsAccountNr());
    assertNotNull(user.getSavingsGoals());
    assertNotNull(user.getChallenges());
    assertNotNull(user.getEarnedBadges());
  }

  @Test
  public void testUserConstructorFromDto() {
    UserDto userDto = new UserDto();
    userDto.setId(1L);
    userDto.setUsername("testUser");
    userDto.setPassword("testPassword");
    userDto.setEmail("test@example.com");
    userDto.setProfilePictureUrl("https://example.com/profile.jpg");

    User userFromDto = new User(userDto);

    assertEquals(userDto.getId(), userFromDto.getId());
    assertEquals(userDto.getUsername(), userFromDto.getUsername());
    assertEquals(userDto.getPassword(), userFromDto.getPassword());
    assertEquals(userDto.getEmail(), userFromDto.getEmail());
    assertEquals(userDto.getProfilePictureUrl(), userFromDto.getProfilePictureUrl());
  }

  @Test
  public void testAddChallenge() {
    Challenge challenge = new Challenge();
    user.addChallenge(challenge);

    assertEquals(1, user.getChallenges().size());
    assertEquals(challenge, user.getChallenges().get(0));
  }

  @Test
  public void testRemoveChallenge() {
    Challenge challenge = new Challenge();
    user.addChallenge(challenge);
    user.removeChallenge(challenge);

    assertEquals(0, user.getChallenges().size());
  }
}
