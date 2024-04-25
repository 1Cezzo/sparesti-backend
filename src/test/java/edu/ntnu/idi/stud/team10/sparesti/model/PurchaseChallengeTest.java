package edu.ntnu.idi.stud.team10.sparesti.model;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PurchaseChallengeTest {

  private PurchaseChallenge purchaseChallenge;
  private final String testDescription = "Test Description";
  private final double testTargetAmount = 500.0;
  private final double testSavedAmount = 100.0;
  private final String testMediaUrl = "http://example.com";
  private final TimeInterval testTimeInterval = TimeInterval.WEEKLY;
  private final DifficultyLevel testDifficultyLevel = DifficultyLevel.EASY;
  private final LocalDate testExpiryDate = LocalDate.now().plusDays(7);
  private final boolean testCompleted = false;
  private final String testProductName = "Test Product";

  @BeforeEach
  public void setUp() {
    purchaseChallenge = new PurchaseChallenge();
    purchaseChallenge.setDescription(testDescription);
    purchaseChallenge.setTargetAmount(testTargetAmount);
    purchaseChallenge.setSavedAmount(testSavedAmount);
    purchaseChallenge.setMediaUrl(testMediaUrl);
    purchaseChallenge.setTimeInterval(testTimeInterval);
    purchaseChallenge.setDifficultyLevel(testDifficultyLevel);
    purchaseChallenge.setExpiryDate(testExpiryDate);
    purchaseChallenge.setCompleted(testCompleted);
    purchaseChallenge.setProductName(testProductName);
  }

  @Test
  public void testPurchaseChallengeAttributes() {
    assertNotNull(purchaseChallenge);
    assertEquals(testDescription, purchaseChallenge.getDescription());
    assertEquals(testTargetAmount, purchaseChallenge.getTargetAmount());
    assertEquals(testSavedAmount, purchaseChallenge.getSavedAmount());
    assertEquals(testMediaUrl, purchaseChallenge.getMediaUrl());
    assertEquals(testTimeInterval, purchaseChallenge.getTimeInterval());
    assertEquals(testDifficultyLevel, purchaseChallenge.getDifficultyLevel());
    assertEquals(testExpiryDate, purchaseChallenge.getExpiryDate());
    assertEquals(testCompleted, purchaseChallenge.isCompleted());
    assertEquals(testProductName, purchaseChallenge.getProductName());
  }

  // Add more tests as needed...

}
