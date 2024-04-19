package edu.ntnu.idi.stud.team10.sparesti.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;

public class SavingsGoalDTOTest {

    private SavingsGoalDTO savingsGoalDto;

    @BeforeEach
    public void setUp() {
        savingsGoalDto = new SavingsGoalDTO();
        savingsGoalDto.setName("Test Savings Goal");
        savingsGoalDto.setTargetAmount(1000.0);
        savingsGoalDto.setSavedAmount(500.0);
        savingsGoalDto.setMediaUrl("https://example.com/image.jpg");
        savingsGoalDto.setDeadline(LocalDate.now().plusMonths(6));
        savingsGoalDto.setCompleted(false);
    }

    @Test
    public void testSavingsGoalDtoFields() {
        assertEquals("Test Savings Goal", savingsGoalDto.getName());
        assertEquals(1000.0, savingsGoalDto.getTargetAmount());
        assertEquals(500.0, savingsGoalDto.getSavedAmount());
        assertEquals("https://example.com/image.jpg", savingsGoalDto.getMediaUrl());
        assertEquals(LocalDate.now().plusMonths(6), savingsGoalDto.getDeadline());
        assertEquals(false, savingsGoalDto.isCompleted());
    }

    @Test
    public void testSavingsGoalDtoConversionToEntity() {
        SavingsGoal savingsGoal = savingsGoalDto.toEntity();

        assertEquals("Test Savings Goal", savingsGoal.getName());
        assertEquals(1000.0, savingsGoal.getTargetAmount());
        assertEquals(500.0, savingsGoal.getSavedAmount());
        assertEquals("https://example.com/image.jpg", savingsGoal.getMediaUrl());
        assertEquals(LocalDate.now().plusMonths(6), savingsGoal.getDeadline());
        assertEquals(false, savingsGoal.isCompleted());
    }
}
