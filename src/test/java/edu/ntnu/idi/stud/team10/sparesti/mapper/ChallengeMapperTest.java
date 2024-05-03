package edu.ntnu.idi.stud.team10.sparesti.mapper;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.dto.ChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.ConsumptionChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.PurchaseChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.SavingChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;
import edu.ntnu.idi.stud.team10.sparesti.model.Challenge;
import edu.ntnu.idi.stud.team10.sparesti.model.ConsumptionChallenge;
import edu.ntnu.idi.stud.team10.sparesti.model.PurchaseChallenge;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingChallenge;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

class ChallengeMapperTest {
  private final ChallengeMapper challengeMapper = ChallengeMapper.INSTANCE;
  private ConsumptionChallengeDto consumptionChallengeDto;
  private PurchaseChallengeDto purchaseChallengeDto;
  private SavingChallengeDto savingChallengeDto;

  @BeforeEach
  public void setUp() {
    consumptionChallengeDto = new ConsumptionChallengeDto();
    purchaseChallengeDto = new PurchaseChallengeDto();
    savingChallengeDto = new SavingChallengeDto();

    List<ChallengeDto> challenges =
        List.of(consumptionChallengeDto, purchaseChallengeDto, savingChallengeDto);
    challenges.forEach(
        challengeDto -> {
          challengeDto.setDescription("Test Challenge");
          challengeDto.setTargetAmount(1000.0);
          challengeDto.setUsedAmount(500.0);
          challengeDto.setMediaUrl("https://example.com/image.jpg");
          challengeDto.setTimeInterval(TimeInterval.MONTHLY);
          challengeDto.setDifficultyLevel(DifficultyLevel.MEDIUM);
          challengeDto.setExpiryDate(LocalDate.now().plusMonths(1));
          challengeDto.setCompleted(false);
        });

    consumptionChallengeDto.setProductCategory("Test Category");
    consumptionChallengeDto.setReductionPercentage(20.0);

    purchaseChallengeDto.setProductName("Test Product");
  }

  @Test
  void toEntityInstantiatesCorrectSubclass() {
    Challenge consumptionChallenge = challengeMapper.toEntity(consumptionChallengeDto);
    Challenge purchaseChallenge = challengeMapper.toEntity(purchaseChallengeDto);
    Challenge savingChallenge = challengeMapper.toEntity(savingChallengeDto);

    assertDoesNotThrow(() -> (ConsumptionChallenge) consumptionChallenge);
    assertDoesNotThrow(() -> (PurchaseChallenge) purchaseChallenge);
    assertDoesNotThrow(() -> (SavingChallenge) savingChallenge);
  }

  @Test
  void toDtoInstantiatesCorrectSubclass() {
    ChallengeDto consumptionChallengeDto = challengeMapper.toDto(new ConsumptionChallenge());
    ChallengeDto purchaseChallengeDto = challengeMapper.toDto(new PurchaseChallenge());
    ChallengeDto savingChallengeDto = challengeMapper.toDto(new SavingChallenge());

    assertDoesNotThrow(() -> (ConsumptionChallengeDto) consumptionChallengeDto);
    assertDoesNotThrow(() -> (PurchaseChallengeDto) purchaseChallengeDto);
    assertDoesNotThrow(() -> (SavingChallengeDto) savingChallengeDto);
  }

  @Test
  void shouldConvertToEntityWithSameValues() {
    ChallengeDto challengeDto = new ChallengeDto();
    challengeDto.setDescription("Test Challenge");
    challengeDto.setTargetAmount(1000.0);
    challengeDto.setUsedAmount(500.0);
    challengeDto.setMediaUrl("https://example.com/image.jpg");
    challengeDto.setTimeInterval(TimeInterval.MONTHLY);
    challengeDto.setDifficultyLevel(DifficultyLevel.MEDIUM);
    challengeDto.setExpiryDate(LocalDate.now().plusMonths(1));
    challengeDto.setCompleted(false);

    Challenge challenge = challengeMapper.toEntity(challengeDto);

    assertEquals(challengeDto.getDescription(), challenge.getDescription());
    assertEquals(challengeDto.getTargetAmount(), challenge.getTargetAmount());
    assertEquals(challengeDto.getUsedAmount(), challenge.getUsedAmount());
    assertEquals(challengeDto.getMediaUrl(), challenge.getMediaUrl());
    assertEquals(challengeDto.getTimeInterval(), challenge.getTimeInterval());
    assertEquals(challengeDto.getDifficultyLevel(), challenge.getDifficultyLevel());
    assertEquals(challengeDto.getExpiryDate(), challenge.getExpiryDate());
    assertEquals(challengeDto.isCompleted(), challenge.isCompleted());
  }

  @Test
  void shouldCreateEntityWithNullValuesWhenDtoFieldsAreNull() {
    ChallengeDto challengeDto = new ChallengeDto();
    challengeDto.setDescription(null);
    challengeDto.setTargetAmount(0.0);
    challengeDto.setUsedAmount(0.0);
    challengeDto.setMediaUrl(null);
    challengeDto.setTimeInterval(null);
    challengeDto.setDifficultyLevel(null);
    challengeDto.setExpiryDate(null);
    challengeDto.setCompleted(false);

    Challenge challenge = challengeMapper.toEntity(challengeDto);

    assertNull(challenge.getDescription());
    assertEquals(0.0, challenge.getTargetAmount());
    assertEquals(0.0, challenge.getUsedAmount());
    assertNull(challenge.getMediaUrl());
    assertNull(challenge.getTimeInterval());
    assertNull(challenge.getDifficultyLevel());
    assertNull(challenge.getExpiryDate());
    assertFalse(challenge.isCompleted());
  }

  @Test
  void testConsumptionChallengeDtoConversionToEntity() {
    ConsumptionChallenge consumptionChallenge =
        (ConsumptionChallenge) challengeMapper.toEntity(consumptionChallengeDto);

    assertEquals("Test Category", consumptionChallenge.getProductCategory());
    assertEquals(20.0, consumptionChallenge.getReductionPercentage());
  }

  @Test
  void testPurchaseChallengeDtoConversionToEntity() {
    PurchaseChallenge purchaseChallenge =
        (PurchaseChallenge) challengeMapper.toEntity(purchaseChallengeDto);

    assertEquals("Test Product", purchaseChallenge.getProductName());
  }
}
