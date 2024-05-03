package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.ntnu.idi.stud.team10.sparesti.dto.PurchaseChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;
import edu.ntnu.idi.stud.team10.sparesti.mapper.ChallengeMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.PurchaseChallenge;
import edu.ntnu.idi.stud.team10.sparesti.repository.PurchaseChallengeRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PurchaseChallengeServiceTest {
  @Mock private PurchaseChallengeRepository purchaseChallengeRepository;

  @Mock private ChallengeMapper challengeMapper;

  @InjectMocks private PurchaseChallengeService purchaseChallengeService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreatePurchaseChallenge() {
    PurchaseChallenge challenge = new PurchaseChallenge();
    PurchaseChallengeDto dto = new PurchaseChallengeDto();
    challenge.setTimeInterval(TimeInterval.WEEKLY);
    dto.setTimeInterval(TimeInterval.WEEKLY);

    when(challengeMapper.toEntity(dto)).thenReturn(challenge);
    when(purchaseChallengeRepository.save(challenge)).thenReturn(challenge);

    PurchaseChallenge createdChallenge = purchaseChallengeService.createChallenge(challenge);

    assertNotNull(createdChallenge);
  }

  @Test
  void testUpdatePurchaseChallenge() {
    PurchaseChallenge challenge = new PurchaseChallenge();
    challenge.setId(1L);
    PurchaseChallenge updatedChallenge = new PurchaseChallenge();
    updatedChallenge.setDescription("New Description");

    when(purchaseChallengeRepository.findById(1L)).thenReturn(Optional.of(challenge));
    when(purchaseChallengeRepository.save(challenge)).thenReturn(updatedChallenge);

    PurchaseChallenge result = purchaseChallengeService.updateChallenge(1L, updatedChallenge);

    assertNotNull(result);
  }

  @Test
  void testAddToSavedAmount() {
    PurchaseChallenge challenge = new PurchaseChallenge();
    challenge.setId(1L);
    challenge.setUsedAmount(0.0);
    challenge.setTargetAmount(100.0);

    when(purchaseChallengeRepository.findById(1L)).thenReturn(Optional.of(challenge));
    when(purchaseChallengeRepository.save(challenge)).thenReturn(challenge);

    purchaseChallengeService.addToSavedAmount(challenge.getId(), 50);

    assertEquals(50, challenge.getUsedAmount());
  }

  @Test
  void testDeleteChallenge() {
    PurchaseChallenge challenge = new PurchaseChallenge();
    challenge.setId(1L);

    when(purchaseChallengeRepository.findById(1L)).thenReturn(Optional.of(challenge));

    purchaseChallengeService.deletePurchaseChallenge(1L);

    verify(purchaseChallengeRepository, times(1)).delete(challenge);
  }

  @Test
  void testGetAllChallenges() {
    when(purchaseChallengeRepository.findAll()).thenReturn(null);

    purchaseChallengeService.getAllPurchaseChallenges();

    verify(purchaseChallengeRepository, times(1)).findAll();
  }

  @Test
  void testGetPurchaseChallengeById() {
    PurchaseChallenge challenge = new PurchaseChallenge();
    when(purchaseChallengeRepository.findById(1L)).thenReturn(Optional.of(challenge));

    when(purchaseChallengeRepository.findById(1L)).thenReturn(Optional.of(challenge));

    purchaseChallengeService.getPurchaseChallengeById(1L);

    verify(purchaseChallengeRepository, times(1)).findById(1L);
  }
}
