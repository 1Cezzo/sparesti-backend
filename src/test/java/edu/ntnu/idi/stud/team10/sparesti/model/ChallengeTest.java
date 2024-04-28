package edu.ntnu.idi.stud.team10.sparesti.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChallengeTest {

  private Challenge challenge;
  private final Long testId = 1L;
  private final String testName = "Test Challenge";
  private final String testDescription = "Test Description";
  private final double testTargetAmount = 500.0;
  private final double testUsedAmount = 100.0;
  private final String testMediaUrl = "http://example.com";
  private final TimeInterval testTimeInterval = TimeInterval.WEEKLY;
  private final DifficultyLevel testDifficultyLevel = DifficultyLevel.EASY;
  private final LocalDate testExpiryDate = LocalDate.now().plusDays(7);
  private final boolean testCompleted = false;
  private List<User> testUsers;

  @BeforeEach
  public void setUp() {
    challenge =
        new Challenge(
            testId,
            testName,
            testDescription,
            testTargetAmount,
            testUsedAmount,
            testMediaUrl,
            testTimeInterval,
            testDifficultyLevel,
            testExpiryDate,
            testCompleted,
            testUsers);
  }

  @Test
  public void testChallengeAttributes() {
    assertNotNull(challenge);
    assertEquals(testId, challenge.getId());
    assertEquals(testDescription, challenge.getDescription());
    assertEquals(testTargetAmount, challenge.getTargetAmount());
    assertEquals(testUsedAmount, challenge.getUsedAmount());
    assertEquals(testMediaUrl, challenge.getMediaUrl());
    assertEquals(testTimeInterval, challenge.getTimeInterval());
    assertEquals(testDifficultyLevel, challenge.getDifficultyLevel());
    assertEquals(testExpiryDate, challenge.getExpiryDate());
    assertEquals(testCompleted, challenge.isCompleted());
  }

  @Test
  public void testEqualsAndHashCode() {
    Challenge challenge1 =
        new Challenge(
            testId,
            testName,
            testDescription,
            testTargetAmount,
            testUsedAmount,
            testMediaUrl,
            testTimeInterval,
            testDifficultyLevel,
            testExpiryDate,
            testCompleted,
            testUsers);
    Challenge challenge2 =
        new Challenge(
            testId,
            testName,
            testDescription,
            testTargetAmount,
            testUsedAmount,
            testMediaUrl,
            testTimeInterval,
            testDifficultyLevel,
            testExpiryDate,
            testCompleted,
            testUsers);
    Challenge challenge3 =
        new Challenge(
            2L,
            "Another Challenge",
            "Another Challenge",
            1000.0,
            200.0,
            "http://anotherexample.com",
            TimeInterval.MONTHLY,
            DifficultyLevel.HARD,
            LocalDate.now().plusDays(14),
            true,
            new ArrayList<>());

    assertEquals(challenge1, challenge2);
    assertNotEquals(challenge1, challenge3);
    assertEquals(challenge1.hashCode(), challenge2.hashCode());
    assertNotEquals(challenge1.hashCode(), challenge3.hashCode());
  }

  @Test
  public void testToString() {
    String expectedToString =
        "Challenge(id=1, title=Test Challenge, description=Test Description, targetAmount=500.0, usedAmount=100.0, mediaUrl=http://example.com, timeInterval=WEEKLY, difficultyLevel=EASY, expiryDate="
            + testExpiryDate
            + ", completed=false, users=null)";
    assertEquals(expectedToString, challenge.toString());
  }

  @Test
  public void testConstructor() {
    assertEquals(testId, challenge.getId());
    assertEquals(testDescription, challenge.getDescription());
    assertEquals(testTargetAmount, challenge.getTargetAmount());
    assertEquals(testUsedAmount, challenge.getUsedAmount());
    assertEquals(testMediaUrl, challenge.getMediaUrl());
    assertEquals(testTimeInterval, challenge.getTimeInterval());
    assertEquals(testDifficultyLevel, challenge.getDifficultyLevel());
    assertEquals(testExpiryDate, challenge.getExpiryDate());
    assertEquals(testCompleted, challenge.isCompleted());
    assertEquals(testUsers, challenge.getUsers());
  }
}
