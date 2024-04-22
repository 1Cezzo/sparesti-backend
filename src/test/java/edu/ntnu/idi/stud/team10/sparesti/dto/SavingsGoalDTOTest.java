package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SavingsGoalDTOTest {

  private SavingsGoalDTO savingsGoalDto1;
  private SavingsGoalDTO savingsGoalDto2;

  @BeforeEach
  public void setUp() {
    savingsGoalDto1 = new SavingsGoalDTO();
    savingsGoalDto1.setName("Test Savings Goal");
    savingsGoalDto1.setTargetAmount(1000.0);
    savingsGoalDto1.setSavedAmount(500.0);
    savingsGoalDto1.setMediaUrl("https://example.com/image.jpg");
    savingsGoalDto1.setDeadline(LocalDate.now().plusMonths(6));
    savingsGoalDto1.setCompleted(false);

    savingsGoalDto2 = new SavingsGoalDTO();
    savingsGoalDto2.setName("Test Savings Goal");
    savingsGoalDto2.setTargetAmount(1000.0);
    savingsGoalDto2.setSavedAmount(500.0);
    savingsGoalDto2.setMediaUrl("https://example.com/image.jpg");
    savingsGoalDto2.setDeadline(LocalDate.now().plusMonths(6));
    savingsGoalDto2.setCompleted(false);
  }

  @Test
  public void testSavingsGoalDtoFields() {
    assertEquals("Test Savings Goal", savingsGoalDto1.getName());
    assertEquals(1000.0, savingsGoalDto1.getTargetAmount());
    assertEquals(500.0, savingsGoalDto1.getSavedAmount());
    assertEquals("https://example.com/image.jpg", savingsGoalDto1.getMediaUrl());
    assertEquals(LocalDate.now().plusMonths(6), savingsGoalDto1.getDeadline());
    assertEquals(false, savingsGoalDto1.isCompleted());
  }

  @Test
  public void testSavingsGoalDtoConversionToEntity() {
    SavingsGoal savingsGoal = savingsGoalDto1.toEntity();

    assertEquals("Test Savings Goal", savingsGoal.getName());
    assertEquals(1000.0, savingsGoal.getTargetAmount());
    assertEquals(500.0, savingsGoal.getSavedAmount());
    assertEquals("https://example.com/image.jpg", savingsGoal.getMediaUrl());
    assertEquals(LocalDate.now().plusMonths(6), savingsGoal.getDeadline());
    assertEquals(false, savingsGoal.isCompleted());
  }

  @Test
  public void testEquals() {
    assertTrue(savingsGoalDto1.equals(savingsGoalDto2));
  }

  @Test
  public void testNotEquals() {
    savingsGoalDto2.setName("Different Name");
    assertFalse(savingsGoalDto1.equals(savingsGoalDto2));
  }

  @Test
  public void testHashCode() {
    assertEquals(savingsGoalDto1.hashCode(), savingsGoalDto2.hashCode());
  }
}
