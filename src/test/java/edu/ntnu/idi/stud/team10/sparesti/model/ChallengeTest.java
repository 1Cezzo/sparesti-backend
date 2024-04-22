package edu.ntnu.idi.stud.team10.sparesti.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChallengeTest {

  private Challenge challenge;
  private final String testDescription = "Test Description";
  private final double testTargetAmount = 500.0;
  private final double testSavedAmount = 100.0;
  private final String testMediaUrl = "http://example.com";
  private final TimeInterval testTimeInterval = TimeInterval.WEEKLY;
  private final DifficultyLevel testDifficultyLevel = DifficultyLevel.EASY;
  private final LocalDate testExpiryDate = LocalDate.now().plusDays(7);
  private final boolean testCompleted = false;
  private List<User> testUsers;

  @BeforeEach
  public void setUp() {
    challenge = new Challenge();
    challenge.setDescription(testDescription);
    challenge.setTargetAmount(testTargetAmount);
    challenge.setSavedAmount(testSavedAmount);
    challenge.setMediaUrl(testMediaUrl);
    challenge.setTimeInterval(testTimeInterval);
    challenge.setDifficultyLevel(testDifficultyLevel);
    challenge.setExpiryDate(testExpiryDate);
    challenge.setCompleted(testCompleted);
    testUsers = new ArrayList<>();
  }

  @Test
  public void testChallengeAttributes() {
    assertNotNull(challenge);
    assertEquals(testDescription, challenge.getDescription());
    assertEquals(testTargetAmount, challenge.getTargetAmount());
    assertEquals(testSavedAmount, challenge.getSavedAmount());
    assertEquals(testMediaUrl, challenge.getMediaUrl());
    assertEquals(testTimeInterval, challenge.getTimeInterval());
    assertEquals(testDifficultyLevel, challenge.getDifficultyLevel());
    assertEquals(testExpiryDate, challenge.getExpiryDate());
    assertEquals(testCompleted, challenge.isCompleted());
  }

  @Test
  public void testUsersList() {
    challenge.setUsers(testUsers);
    assertEquals(testUsers, challenge.getUsers());
  }

  // Add more tests as needed...

}
