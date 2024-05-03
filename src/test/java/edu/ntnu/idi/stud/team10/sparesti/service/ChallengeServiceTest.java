package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;
import edu.ntnu.idi.stud.team10.sparesti.model.Challenge;
import edu.ntnu.idi.stud.team10.sparesti.model.ConsumptionChallenge;
import edu.ntnu.idi.stud.team10.sparesti.model.PurchaseChallenge;
import edu.ntnu.idi.stud.team10.sparesti.repository.ChallengeRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ChallengeServiceTest {
  @Mock private ChallengeRepository<Challenge> challengeRepository;
  private ChallengeService<Challenge> challengeService;

  @BeforeEach
  public void setUp() {
    challengeService =
        new ChallengeService<>(challengeRepository) {
          @Override
          protected LocalDate calculateExpiryDate(TimeInterval timeInterval) {
            return LocalDate.now().plusDays(1);
          }
        };
  }

  @Test
  void testCreateChallenge() {
    Challenge challenge = new Challenge();
    when(challengeRepository.save(any())).thenReturn(challenge);

    Challenge createdChallenge = challengeService.createChallenge(challenge);

    assertNotNull(createdChallenge);
    assertEquals(0.0, createdChallenge.getUsedAmount());
    assertFalse(createdChallenge.isCompleted());
  }

  @Test
  void testUpdateChallenge() {
    Challenge challenge = new Challenge();
    challenge.setId(1L);
    when(challengeRepository.findById(any())).thenReturn(Optional.of(challenge));

    ConsumptionChallenge updatedChallenge = new ConsumptionChallenge();
    updatedChallenge.setDescription("New Description");

    when(challengeRepository.save(any())).thenReturn(challenge);

    Challenge result = challengeService.updateChallenge(1L, updatedChallenge);

    assertNotNull(result);
    assertEquals("New Description", result.getDescription());
  }

  @Test
  void testAddToSavedAmount() {
    Challenge challenge = new Challenge();
    challenge.setId(1L);
    challenge.setUsedAmount(50.0);
    challenge.setTargetAmount(100.0);
    when(challengeRepository.findById(any())).thenReturn(Optional.of(challenge));

    challengeService.addToSavedAmount(1L, 25.0);

    assertEquals(75.0, challenge.getUsedAmount());
    assertFalse(challenge.isCompleted());

    challengeService.addToSavedAmount(1L, 30.0); // Change this line

    assertEquals(105.0, challenge.getUsedAmount()); // And this line
    assertFalse(challenge.isCompleted());
  }

  @Test
  void testGetAllChallenges() {
    when(challengeRepository.findAll()).thenReturn(new ArrayList<>());

    List<Challenge> result = challengeService.getAll();

    assertNotNull(result);
    verify(challengeRepository, times(1)).findAll();
  }

  @Test
  void testGetById() {
    Challenge challenge = new Challenge();
    when(challengeRepository.findById(any())).thenReturn(Optional.of(challenge));

    Optional<Challenge> result = challengeService.getById(1L);

    assertNotNull(result);
    verify(challengeRepository, times(1)).findById(1L);
  }

  @Test
  void testDeleteChallenge() {
    Challenge challenge = new Challenge();
    when(challengeRepository.findById(any())).thenReturn(Optional.of(challenge));

    challengeService.deleteChallenge(1L);

    verify(challengeRepository, times(1)).delete(challenge);
  }

  @Test
  void updateConsumptionChallengeTest() {
    ConsumptionChallenge challenge = new ConsumptionChallenge();
    challenge.setId(1L);
    when(challengeRepository.findById(any())).thenReturn(Optional.of(challenge));

    ConsumptionChallenge updatedChallenge = new ConsumptionChallenge();
    updatedChallenge.setDescription("New Description");
    updatedChallenge.setTargetAmount(100.0);
    updatedChallenge.setUsedAmount(50.0);
    updatedChallenge.setCompleted(true);
    updatedChallenge.setExpiryDate(LocalDate.now().plusDays(1));
    updatedChallenge.setDifficultyLevel(DifficultyLevel.EASY);
    updatedChallenge.setTimeInterval(TimeInterval.DAILY);
    updatedChallenge.setTitle("New Title");
    updatedChallenge.setMediaUrl("http://example.com/image.jpg");
    updatedChallenge.setProductCategory("New Category");
    updatedChallenge.setReductionPercentage(10.0);

    when(challengeRepository.save(any())).thenReturn(challenge);

    ConsumptionChallenge result = challengeService.updateChallenge(1L, updatedChallenge);

    assertNotNull(result);
    assertEquals("New Description", result.getDescription());
    assertEquals(100.0, result.getTargetAmount());
    assertEquals(50.0, result.getUsedAmount());
    assertFalse(result.isCompleted());
    assertEquals(LocalDate.now().plusDays(1), result.getExpiryDate());
    assertEquals(TimeInterval.DAILY, result.getTimeInterval());
    assertEquals(DifficultyLevel.EASY, result.getDifficultyLevel());
    assertEquals("New Title", result.getTitle());
    assertEquals("http://example.com/image.jpg", result.getMediaUrl());
    assertEquals("New Category", result.getProductCategory());
    assertEquals(10.0, result.getReductionPercentage());
  }

  @Test
  void updatedPurchaseChallengeTest() {
    PurchaseChallenge challenge = new PurchaseChallenge();
    challenge.setId(1L);
    when(challengeRepository.findById(any())).thenReturn(Optional.of(challenge));

    PurchaseChallenge updatedChallenge = new PurchaseChallenge();
    updatedChallenge.setDescription("New Description");
    updatedChallenge.setTargetAmount(100.0);
    updatedChallenge.setUsedAmount(50.0);
    updatedChallenge.setCompleted(true);
    updatedChallenge.setExpiryDate(LocalDate.now().plusDays(1));
    updatedChallenge.setDifficultyLevel(DifficultyLevel.EASY);
    updatedChallenge.setTimeInterval(TimeInterval.DAILY);
    updatedChallenge.setTitle("New Title");
    updatedChallenge.setMediaUrl("http://example.com/image.jpg");
    updatedChallenge.setProductName("New Product");
    updatedChallenge.setProductPrice(10.0);

    when(challengeRepository.save(any())).thenReturn(challenge);

    PurchaseChallenge result = challengeService.updateChallenge(1L, updatedChallenge);

    assertNotNull(result);
    assertEquals("New Description", result.getDescription());
    assertEquals(100.0, result.getTargetAmount());
    assertEquals(50.0, result.getUsedAmount());
    assertFalse(result.isCompleted());
    assertEquals(LocalDate.now().plusDays(1), result.getExpiryDate());
    assertEquals(TimeInterval.DAILY, result.getTimeInterval());
    assertEquals(DifficultyLevel.EASY, result.getDifficultyLevel());
    assertEquals("New Title", result.getTitle());
    assertEquals("http://example.com/image.jpg", result.getMediaUrl());
    assertEquals("New Product", result.getProductName());
    assertEquals(10.0, result.getProductPrice());
  }
}
