package edu.ntnu.idi.stud.team10.sparesti.model;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConsumptionChallengeTest {

  private ConsumptionChallenge consumptionChallenge;
  private final String testDescription = "Test Description";
  private final double testTargetAmount = 500.0;
  private final double testSavedAmount = 100.0;
  private final String testMediaUrl = "http://example.com";
  private final TimeInterval testTimeInterval = TimeInterval.WEEKLY;
  private final DifficultyLevel testDifficultyLevel = DifficultyLevel.EASY;
  private final LocalDate testExpiryDate = LocalDate.now().plusDays(7);
  private final boolean testCompleted = false;
  private final String testProductCategory = "Test Category";
  private final double testReductionPercentage = 10.0;

  @BeforeEach
  public void setUp() {
    consumptionChallenge = new ConsumptionChallenge();
    consumptionChallenge.setDescription(testDescription);
    consumptionChallenge.setTargetAmount(testTargetAmount);
    consumptionChallenge.setSavedAmount(testSavedAmount);
    consumptionChallenge.setMediaUrl(testMediaUrl);
    consumptionChallenge.setTimeInterval(testTimeInterval);
    consumptionChallenge.setDifficultyLevel(testDifficultyLevel);
    consumptionChallenge.setExpiryDate(testExpiryDate);
    consumptionChallenge.setCompleted(testCompleted);
    consumptionChallenge.setProductCategory(testProductCategory);
    consumptionChallenge.setReductionPercentage(testReductionPercentage);
  }

  @Test
  public void testConsumptionChallengeAttributes() {
    assertNotNull(consumptionChallenge);
    assertEquals(testDescription, consumptionChallenge.getDescription());
    assertEquals(testTargetAmount, consumptionChallenge.getTargetAmount());
    assertEquals(testSavedAmount, consumptionChallenge.getSavedAmount());
    assertEquals(testMediaUrl, consumptionChallenge.getMediaUrl());
    assertEquals(testTimeInterval, consumptionChallenge.getTimeInterval());
    assertEquals(testDifficultyLevel, consumptionChallenge.getDifficultyLevel());
    assertEquals(testExpiryDate, consumptionChallenge.getExpiryDate());
    assertEquals(testCompleted, consumptionChallenge.isCompleted());
    assertEquals(testProductCategory, consumptionChallenge.getProductCategory());
    assertEquals(testReductionPercentage, consumptionChallenge.getReductionPercentage());
  }

  // Add more tests as needed...

}
