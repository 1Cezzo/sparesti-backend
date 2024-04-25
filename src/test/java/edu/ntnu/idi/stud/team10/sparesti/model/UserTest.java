package edu.ntnu.idi.stud.team10.sparesti.model;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

  private User user;

  @BeforeEach
  public void setUp() {
    user = new User();
    user.setId(1L);
    user.setPassword("testPassword");
    user.setEmail("test@example.com");
    user.setTotalSavings(1000.0);
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
    assertEquals("testPassword", user.getPassword());
    assertEquals("test@example.com", user.getEmail());
    assertEquals(1000.0, user.getTotalSavings());
    assertEquals("https://example.com/profile.jpg", user.getProfilePictureUrl());
    assertEquals(123456, user.getCheckingAccountNr());
    assertEquals(789012, user.getSavingsAccountNr());
    assertNotNull(user.getSavingsGoals());
    assertNotNull(user.getChallenges());
    assertNotNull(user.getEarnedBadges());
  }

  @Test
  public void testAddAndRemoveChallenge() {
    Challenge challenge = new Challenge();
    user.addChallenge(challenge);

    assertEquals(1, user.getChallenges().size());
    assertEquals(challenge, user.getChallenges().get(0));

    user.removeChallenge(challenge);
    assertEquals(0, user.getChallenges().size());
  }

  @Test
  public void testAddAndRemoveBadge() {
    Badge badge = new Badge();
    user.addBadge(badge);

    assertEquals(1, user.getEarnedBadges().size());
    assertTrue(user.getEarnedBadges().contains(badge));

    user.removeBadge(badge);
    assertEquals(0, user.getEarnedBadges().size());
  }
}
