package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.ntnu.idi.stud.team10.sparesti.dto.SavingChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;
import edu.ntnu.idi.stud.team10.sparesti.mapper.ChallengeMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingChallenge;
import edu.ntnu.idi.stud.team10.sparesti.repository.SavingChallengeRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SavingChallengeServiceTest {
  @Mock private SavingChallengeRepository savingChallengeRepository;

  @Mock private ChallengeMapper challengeMapper;

  @InjectMocks private SavingChallengeService savingChallengeService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateSavingChallenge() {
    SavingChallenge challenge = new SavingChallenge();
    SavingChallengeDto dto = new SavingChallengeDto();
    challenge.setTimeInterval(TimeInterval.WEEKLY);
    dto.setTimeInterval(TimeInterval.WEEKLY);

    when(challengeMapper.toEntity(dto)).thenReturn(challenge);
    when(savingChallengeRepository.save(challenge)).thenReturn(challenge);

    SavingChallenge createdChallenge = savingChallengeService.createChallenge(challenge);

    assertNotNull(createdChallenge);
  }

  @Test
  void testUpdateSavingChallenge() {
    SavingChallenge challenge = new SavingChallenge();
    challenge.setId(1L);
    SavingChallenge updatedChallenge = new SavingChallenge();
    updatedChallenge.setDescription("New Description");

    when(savingChallengeRepository.findById(1L)).thenReturn(Optional.of(challenge));
    when(savingChallengeRepository.save(challenge)).thenReturn(updatedChallenge);

    SavingChallenge result = savingChallengeService.updateChallenge(1L, updatedChallenge);

    assertNotNull(result);
  }

  @Test
  void testAddToSavedAmount() {
    SavingChallenge challenge = new SavingChallenge();
    challenge.setId(1L);
    challenge.setUsedAmount(0.0);
    challenge.setTargetAmount(100.0);

    when(savingChallengeRepository.findById(1L)).thenReturn(Optional.of(challenge));
    when(savingChallengeRepository.save(challenge)).thenReturn(challenge);

    savingChallengeService.addToSavedAmount(challenge.getId(), 50);

    assertEquals(50, challenge.getUsedAmount());
  }

  @Test
  void testDeleteChallenge() {
    SavingChallenge challenge = new SavingChallenge();
    challenge.setId(1L);

    when(savingChallengeRepository.findById(1L)).thenReturn(Optional.of(challenge));

    savingChallengeService.deleteSavingChallenge(1L);

    verify(savingChallengeRepository, times(1)).delete(challenge);
  }

  @Test
  void testGetAllChallenges() {
    when(savingChallengeRepository.findAll()).thenReturn(null);

    savingChallengeService.getAllSavingChallenges();

    verify(savingChallengeRepository, times(1)).findAll();
  }

  @Test
  void testGetSavingChallengeById() {
    SavingChallenge challenge = new SavingChallenge();
    when(savingChallengeRepository.findById(1L)).thenReturn(Optional.of(challenge));

    when(savingChallengeRepository.findById(1L)).thenReturn(Optional.of(challenge));

    savingChallengeService.getSavingChallengeById(1L);

    verify(savingChallengeRepository, times(1)).findById(1L);
  }
}
