package edu.ntnu.idi.stud.team10.sparesti.mapper;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.dto.SavingsGoalDto;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SavingsGoalMapperTest {
  private final SavingsGoalMapper savingsGoalMapper = SavingsGoalMapper.INSTANCE;
  private SavingsGoalDto savingsGoalDto1;

  @BeforeEach
  void setUp() {
    savingsGoalDto1 = new SavingsGoalDto();
    savingsGoalDto1.setName("Test Savings Goal");
    savingsGoalDto1.setTargetAmount(1000.0);
    savingsGoalDto1.setSavedAmount(500.0);
    savingsGoalDto1.setMediaUrl("https://example.com/image.jpg");
    savingsGoalDto1.setDeadline(LocalDate.now().plusMonths(6));
    savingsGoalDto1.setCompleted(false);
  }

  @Test
  public void testSavingsGoalDtoConversionToEntity() {
    SavingsGoal savingsGoal = savingsGoalMapper.toEntity(savingsGoalDto1);

    assertEquals("Test Savings Goal", savingsGoal.getName());
    assertEquals(1000.0, savingsGoal.getTargetAmount());
    assertEquals(500.0, savingsGoal.getSavedAmount());
    assertEquals("https://example.com/image.jpg", savingsGoal.getMediaUrl());
    assertEquals(LocalDate.now().plusMonths(6), savingsGoal.getDeadline());
    assertFalse(savingsGoal.isCompleted());
  }

  @Test
  void savingGoalEntityToDtoTest() {
    SavingsGoal savingsGoal = new SavingsGoal();
    savingsGoal.setName("Test Savings Goal");
    savingsGoal.setTargetAmount(1000.0);
    savingsGoal.setSavedAmount(500.0);
    savingsGoal.setMediaUrl("https://example.com/image.jpg");
    savingsGoal.setDeadline(LocalDate.now().plusMonths(6));
    savingsGoal.setCompleted(false);

    SavingsGoalDto savingsGoalDto = savingsGoalMapper.toDto(savingsGoal);

    assertEquals("Test Savings Goal", savingsGoalDto.getName());
    assertEquals(1000.0, savingsGoalDto.getTargetAmount());
    assertEquals(500.0, savingsGoalDto.getSavedAmount());
    assertEquals("https://example.com/image.jpg", savingsGoalDto.getMediaUrl());
    assertEquals(LocalDate.now().plusMonths(6), savingsGoalDto.getDeadline());
    assertFalse(savingsGoalDto.isCompleted());
  }
}
