package edu.ntnu.idi.stud.team10.sparesti.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PurchaseChallengeDtoTest {

  private PurchaseChallengeDto purchaseChallengeDto;

  @BeforeEach
  public void setUp() {
    purchaseChallengeDto = new PurchaseChallengeDto();
    purchaseChallengeDto.setDescription("Test Purchase Challenge");
    purchaseChallengeDto.setTargetAmount(1000.0);
    purchaseChallengeDto.setProductName("Test Product");
  }

  @Test
  public void testPurchaseChallengeDtoFields() {
    assertEquals("Test Purchase Challenge", purchaseChallengeDto.getDescription());
    assertEquals(1000.0, purchaseChallengeDto.getTargetAmount());
    assertEquals("Test Product", purchaseChallengeDto.getProductName());
  }
}
