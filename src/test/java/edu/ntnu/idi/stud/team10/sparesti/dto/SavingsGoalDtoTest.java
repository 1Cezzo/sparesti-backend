package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SavingsGoalDtoTest {

  private SavingsGoalDto savingsGoalDto1;
  private SavingsGoalDto savingsGoalDto2;

  @BeforeEach
  public void setUp() {
    savingsGoalDto1 = new SavingsGoalDto();
    savingsGoalDto1.setName("Test Savings Goal");
    savingsGoalDto1.setTargetAmount(1000.0);
    savingsGoalDto1.setSavedAmount(500.0);
    savingsGoalDto1.setMediaUrl("https://example.com/image.jpg");
    savingsGoalDto1.setDeadline(LocalDate.now().plusMonths(6));
    savingsGoalDto1.setCompleted(false);

    savingsGoalDto2 = new SavingsGoalDto();
    savingsGoalDto2.setName("Test Savings Goal");
    savingsGoalDto2.setTargetAmount(1000.0);
    savingsGoalDto2.setSavedAmount(500.0);
    savingsGoalDto2.setMediaUrl("https://example.com/image.jpg");
    savingsGoalDto2.setDeadline(LocalDate.now().plusMonths(6));
    savingsGoalDto2.setCompleted(false);
  }

  @Test
  void testSavingsGoalDtoFields() {
    assertEquals("Test Savings Goal", savingsGoalDto1.getName());
    assertEquals(1000.0, savingsGoalDto1.getTargetAmount());
    assertEquals(500.0, savingsGoalDto1.getSavedAmount());
    assertEquals("https://example.com/image.jpg", savingsGoalDto1.getMediaUrl());
    assertEquals(LocalDate.now().plusMonths(6), savingsGoalDto1.getDeadline());
    assertFalse(savingsGoalDto1.isCompleted());
  }

  @Nested
  class Constructors {
    @Test
    void noArgsConstructor() {
      SavingsGoalDto savingsGoalDto = new SavingsGoalDto();
      assertNotNull(savingsGoalDto);
    }

    @Test
    void allArgsConstructor() {
      SavingsGoalDto savingsGoalDto =
          new SavingsGoalDto(
              1L,
              "Test Savings Goal",
              1000.0,
              500.0,
              "https://example.com/image.jpg",
              LocalDate.now().plusMonths(6),
              false,
              2L);
      assertNotNull(savingsGoalDto);
    }
  }

  @Test
  void testEquals() {
    assertEquals(savingsGoalDto1, savingsGoalDto2);
  }

  @Test
  void testNotEquals() {
    savingsGoalDto2.setName("Different Name");
    assertNotEquals(savingsGoalDto1, savingsGoalDto2);
  }

  @Test
  void testHashCode() {
    assertEquals(savingsGoalDto1.hashCode(), savingsGoalDto2.hashCode());
  }
}
