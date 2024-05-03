package edu.ntnu.idi.stud.team10.sparesti.model;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SavingChallengeTest {

  private SavingChallenge savingChallenge;
  private final String testDescription = "Test Description";
  private final double testTargetAmount = 500.0;
  private final double testUsedAmount = 100.0;
  private final String testMediaUrl = "http://example.com";
  private final TimeInterval testTimeInterval = TimeInterval.WEEKLY;
  private final DifficultyLevel testDifficultyLevel = DifficultyLevel.EASY;
  private final LocalDate testExpiryDate = LocalDate.now().plusDays(7);
  private final boolean testCompleted = false;

  @BeforeEach
  public void setUp() {
    savingChallenge = new SavingChallenge();
    savingChallenge.setDescription(testDescription);
    savingChallenge.setTargetAmount(testTargetAmount);
    savingChallenge.setUsedAmount(testUsedAmount);
    savingChallenge.setMediaUrl(testMediaUrl);
    savingChallenge.setTimeInterval(testTimeInterval);
    savingChallenge.setDifficultyLevel(testDifficultyLevel);
    savingChallenge.setExpiryDate(testExpiryDate);
    savingChallenge.setCompleted(testCompleted);
  }

  @Test
  public void testSavingChallengeAttributes() {
    assertNotNull(savingChallenge);
    assertEquals(testDescription, savingChallenge.getDescription());
    assertEquals(testTargetAmount, savingChallenge.getTargetAmount());
    assertEquals(testUsedAmount, savingChallenge.getUsedAmount());
    assertEquals(testMediaUrl, savingChallenge.getMediaUrl());
    assertEquals(testTimeInterval, savingChallenge.getTimeInterval());
    assertEquals(testDifficultyLevel, savingChallenge.getDifficultyLevel());
    assertEquals(testExpiryDate, savingChallenge.getExpiryDate());
    assertEquals(testCompleted, savingChallenge.isCompleted());
  }

  // Add more tests as needed...

}
