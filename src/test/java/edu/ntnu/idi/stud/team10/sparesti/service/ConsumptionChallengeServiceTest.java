package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.ntnu.idi.stud.team10.sparesti.dto.ConsumptionChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;
import edu.ntnu.idi.stud.team10.sparesti.mapper.ChallengeMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.ConsumptionChallenge;
import edu.ntnu.idi.stud.team10.sparesti.repository.ConsumptionChallengeRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConsumptionChallengeServiceTest {

  @Mock private ConsumptionChallengeRepository consumptionChallengeRepository;

  @Mock private ChallengeMapper challengeMapper;

  @InjectMocks private ConsumptionChallengeService consumptionChallengeService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateConsumptionChallenge() {
    ConsumptionChallenge challenge = new ConsumptionChallenge();
    ConsumptionChallengeDto dto = new ConsumptionChallengeDto();
    challenge.setTimeInterval(TimeInterval.WEEKLY);
    dto.setTimeInterval(TimeInterval.WEEKLY);

    when(challengeMapper.toEntity(dto)).thenReturn(challenge);
    when(consumptionChallengeRepository.save(challenge)).thenReturn(challenge);

    ConsumptionChallenge createdChallenge = consumptionChallengeService.createChallenge(challenge);

    assertNotNull(createdChallenge);
  }

  @Test
  void testUpdateConsumptionChallenge() {
    ConsumptionChallenge challenge = new ConsumptionChallenge();
    challenge.setId(1L);
    ConsumptionChallenge updatedChallenge = new ConsumptionChallenge();
    updatedChallenge.setDescription("New Description");

    when(consumptionChallengeRepository.findById(1L)).thenReturn(Optional.of(challenge));
    when(consumptionChallengeRepository.save(challenge)).thenReturn(updatedChallenge);

    ConsumptionChallenge result = consumptionChallengeService.updateChallenge(1L, updatedChallenge);

    assertNotNull(result);
    assertEquals("New Description", result.getDescription());
  }

  @Test
  void testAddToSavedAmount() {
    ConsumptionChallenge challenge = new ConsumptionChallenge();
    challenge.setId(1L);
    challenge.setUsedAmount(0.0);
    challenge.setTargetAmount(100.0);

    when(consumptionChallengeRepository.findById(1L)).thenReturn(Optional.of(challenge));
    when(consumptionChallengeRepository.save(challenge)).thenReturn(challenge);

    consumptionChallengeService.addToSavedAmount(challenge.getId(), 50.0);

    assertEquals(50.0, challenge.getUsedAmount());
  }

  @Test
  void testDeleteChallenge() {
    ConsumptionChallenge challenge = new ConsumptionChallenge();
    challenge.setId(1L);

    when(consumptionChallengeRepository.findById(1L)).thenReturn(Optional.of(challenge));

    consumptionChallengeService.deleteConsumptionChallenge(1L);

    verify(consumptionChallengeRepository, times(1)).delete(challenge);
  }

  @Test
  void testGetAllConsumptionChallenges() {
    when(consumptionChallengeRepository.findAll()).thenReturn(null);

    consumptionChallengeService.getAllConsumptionChallenges();

    verify(consumptionChallengeRepository, times(1)).findAll();
  }

  @Test
  void testGetConsumptionChallengeById() {
    ConsumptionChallenge challenge = new ConsumptionChallenge();
    challenge.setId(1L);

    when(consumptionChallengeRepository.findById(1L)).thenReturn(Optional.of(challenge));

    consumptionChallengeService.getConsumptionChallengeById(1L);

    verify(consumptionChallengeRepository, times(1)).findById(1L);
  }
}
