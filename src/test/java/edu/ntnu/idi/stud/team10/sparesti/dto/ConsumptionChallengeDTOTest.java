package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;
import edu.ntnu.idi.stud.team10.sparesti.model.ConsumptionChallenge;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsumptionChallengeDTOTest {

  private ConsumptionChallengeDTO consumptionChallengeDto;

  @BeforeEach
  public void setUp() {
    consumptionChallengeDto = new ConsumptionChallengeDTO();
    consumptionChallengeDto.setDescription("Test Consumption Challenge");
    consumptionChallengeDto.setTargetAmount(1000.0);
    consumptionChallengeDto.setSavedAmount(500.0);
    consumptionChallengeDto.setMediaUrl("https://example.com/image.jpg");
    consumptionChallengeDto.setTimeInterval(TimeInterval.MONTHLY);
    consumptionChallengeDto.setDifficultyLevel(DifficultyLevel.MEDIUM);
    consumptionChallengeDto.setExpiryDate(LocalDate.now().plusMonths(1));
    consumptionChallengeDto.setCompleted(false);
    consumptionChallengeDto.setProductCategory("Test Category");
    consumptionChallengeDto.setReductionPercentage(20.0);
  }

  @Test
  public void testConsumptionChallengeDtoFields() {
    assertEquals("Test Consumption Challenge", consumptionChallengeDto.getDescription());
    assertEquals(1000.0, consumptionChallengeDto.getTargetAmount());
    assertEquals("Test Category", consumptionChallengeDto.getProductCategory());
    assertEquals(20.0, consumptionChallengeDto.getReductionPercentage());
  }

  @Test
  public void testConsumptionChallengeDtoConversionToEntity() {
    ConsumptionChallenge consumptionChallenge =
        (ConsumptionChallenge) consumptionChallengeDto.toEntity();

    assertEquals("Test Consumption Challenge", consumptionChallenge.getDescription());
    assertEquals(1000.0, consumptionChallenge.getTargetAmount());
    assertEquals("Test Category", consumptionChallenge.getProductCategory());
    assertEquals(20.0, consumptionChallenge.getReductionPercentage());
  }
}
