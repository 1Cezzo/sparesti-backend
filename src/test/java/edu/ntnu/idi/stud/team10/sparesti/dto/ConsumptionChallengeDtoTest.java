package edu.ntnu.idi.stud.team10.sparesti.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsumptionChallengeDtoTest {

  private ConsumptionChallengeDto consumptionChallengeDto;

  @BeforeEach
  public void setUp() {
    consumptionChallengeDto = new ConsumptionChallengeDto();
    consumptionChallengeDto.setDescription("Test Consumption Challenge");
    consumptionChallengeDto.setTargetAmount(1000.0);
    consumptionChallengeDto.setProductCategory("Test Category");
    consumptionChallengeDto.setReductionPercentage(20.0);
  }

  @Test
  void testConsumptionChallengeDtoFields() {
    assertEquals("Test Consumption Challenge", consumptionChallengeDto.getDescription());
    assertEquals(1000.0, consumptionChallengeDto.getTargetAmount());
    assertEquals("Test Category", consumptionChallengeDto.getProductCategory());
    assertEquals(20.0, consumptionChallengeDto.getReductionPercentage());
  }
}
