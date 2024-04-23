package edu.ntnu.idi.stud.team10.sparesti.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;

import static org.junit.jupiter.api.Assertions.*;

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

  @Test
  public void testEqualsAndHashCode() {
    Challenge challenge1 = new Challenge(1L, "Test Challenge", 500.0, 100.0, "http://example.com", TimeInterval.WEEKLY, DifficultyLevel.EASY, LocalDate.now().plusDays(7), false, new ArrayList<>());
    Challenge challenge2 = new Challenge(1L, "Test Challenge", 500.0, 100.0, "http://example.com", TimeInterval.WEEKLY, DifficultyLevel.EASY, LocalDate.now().plusDays(7), false, new ArrayList<>());
    Challenge challenge3 = new Challenge(2L, "Another Challenge", 1000.0, 200.0, "http://anotherexample.com", TimeInterval.MONTHLY, DifficultyLevel.HARD, LocalDate.now().plusDays(14), true, new ArrayList<>());

    assertEquals(challenge1, challenge2);
    assertNotEquals(challenge1, challenge3);
    assertEquals(challenge1.hashCode(), challenge2.hashCode());
    assertNotEquals(challenge1.hashCode(), challenge3.hashCode());
  }

  @Test
  public void testConstructor() {
    Challenge challenge = new Challenge(1L, "Test Challenge", 500.0, 100.0, "http://example.com", TimeInterval.WEEKLY, DifficultyLevel.EASY, LocalDate.now().plusDays(7), false, new ArrayList<>());

    assertEquals(1L, challenge.getId());
    assertEquals("Test Challenge", challenge.getDescription());
    assertEquals(500.0, challenge.getTargetAmount());
    assertEquals(100.0, challenge.getSavedAmount());
    assertEquals("http://example.com", challenge.getMediaUrl());
    assertEquals(TimeInterval.WEEKLY, challenge.getTimeInterval());
    assertEquals(DifficultyLevel.EASY, challenge.getDifficultyLevel());
    assertEquals(LocalDate.now().plusDays(7), challenge.getExpiryDate());
    assertFalse(challenge.isCompleted());
    assertNotNull(challenge.getUsers());
  }

  @Test
  public void testToString() {
    Challenge challenge = new Challenge(1L, "Test Challenge", 500.0, 100.0, "http://example.com", TimeInterval.WEEKLY, DifficultyLevel.EASY, LocalDate.now().plusDays(7), false, new ArrayList<>());

    String expectedToString = "Challenge(id=1, description=Test Challenge, targetAmount=500.0, savedAmount=100.0, mediaUrl=http://example.com, timeInterval=WEEKLY, difficultyLevel=EASY, expiryDate=" + LocalDate.now().plusDays(7) + ", completed=false)";
    assertEquals(expectedToString, challenge.toString());
  }
}
