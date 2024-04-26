package edu.ntnu.idi.stud.team10.sparesti.model;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SavingsGoalTest {

  private SavingsGoal savingsGoal;
  private final String testName = "Test Name";
  private final double testTargetAmount = 1000.0;
  private final double testSavedAmount = 500.0;
  private final String testMediaUrl = "http://example.com";
  private final LocalDate testDeadline = LocalDate.now().plusDays(30);
  private final boolean testCompleted = false;
  private User testUser;

  @BeforeEach
  public void setUp() {
    savingsGoal = new SavingsGoal();
    savingsGoal.setName(testName);
    savingsGoal.setTargetAmount(testTargetAmount);
    savingsGoal.setSavedAmount(testSavedAmount);
    savingsGoal.setMediaUrl(testMediaUrl);
    savingsGoal.setDeadline(testDeadline);
    savingsGoal.setCompleted(testCompleted);
    testUser = new User(); // Instantiate test user as needed
  }

  @Test
  public void testSavingsGoalAttributes() {
    assertNotNull(savingsGoal);
    assertEquals(testName, savingsGoal.getName());
    assertEquals(testTargetAmount, savingsGoal.getTargetAmount());
    assertEquals(testSavedAmount, savingsGoal.getSavedAmount());
    assertEquals(testMediaUrl, savingsGoal.getMediaUrl());
    assertEquals(testDeadline, savingsGoal.getDeadline());
    assertEquals(testCompleted, savingsGoal.isCompleted());
  }

  // Add more tests as needed...

}
