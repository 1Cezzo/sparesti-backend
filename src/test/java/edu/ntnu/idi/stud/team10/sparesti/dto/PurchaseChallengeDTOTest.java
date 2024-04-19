package edu.ntnu.idi.stud.team10.sparesti.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;
import edu.ntnu.idi.stud.team10.sparesti.model.PurchaseChallenge;

import java.time.LocalDate;

public class PurchaseChallengeDTOTest {

    private PurchaseChallengeDTO purchaseChallengeDto;

    @BeforeEach
    public void setUp() {
        purchaseChallengeDto = new PurchaseChallengeDTO();
        purchaseChallengeDto.setDescription("Test Purchase Challenge");
        purchaseChallengeDto.setTargetAmount(1000.0);
        purchaseChallengeDto.setSavedAmount(500.0);
        purchaseChallengeDto.setMediaUrl("https://example.com/image.jpg");
        purchaseChallengeDto.setTimeInterval(TimeInterval.MONTHLY);
        purchaseChallengeDto.setDifficultyLevel(DifficultyLevel.MEDIUM);
        purchaseChallengeDto.setExpiryDate(LocalDate.now().plusMonths(1));
        purchaseChallengeDto.setCompleted(false);
        purchaseChallengeDto.setProductName("Test Product");
    }

    @Test
    public void testPurchaseChallengeDtoFields() {
        assertEquals("Test Purchase Challenge", purchaseChallengeDto.getDescription());
        assertEquals(1000.0, purchaseChallengeDto.getTargetAmount());
        assertEquals("Test Product", purchaseChallengeDto.getProductName());
    }

    @Test
    public void testPurchaseChallengeDtoConversionToEntity() {
        PurchaseChallenge purchaseChallenge = (PurchaseChallenge) purchaseChallengeDto.toEntity();

        assertEquals("Test Purchase Challenge", purchaseChallenge.getDescription());
        assertEquals(1000.0, purchaseChallenge.getTargetAmount());
        assertEquals("Test Product", purchaseChallenge.getProductName());
    }
}
