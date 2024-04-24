package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;
import edu.ntnu.idi.stud.team10.sparesti.model.Challenge;
import edu.ntnu.idi.stud.team10.sparesti.model.ConsumptionChallenge;
import edu.ntnu.idi.stud.team10.sparesti.repository.ChallengeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ChallengeServiceTest {

  private ChallengeRepository<Challenge> challengeRepository;
  private UserRepository userRepository;
  private ChallengeService<Challenge> challengeService;

  @BeforeEach
  public void setUp() {
    challengeRepository = Mockito.mock(ChallengeRepository.class);
    userRepository = Mockito.mock(UserRepository.class);
    challengeService =
        new ChallengeService<>(challengeRepository, userRepository) {
          @Override
          protected LocalDate calculateExpiryDate(TimeInterval timeInterval) {
            return LocalDate.now().plusDays(1);
          }
        };
  }

  @Test
  public void testCreateChallenge() {
    Challenge challenge = new Challenge();
    when(challengeRepository.save(any())).thenReturn(challenge);

    Challenge createdChallenge = challengeService.createChallenge(challenge);

    assertNotNull(createdChallenge);
    assertEquals(0.0, createdChallenge.getSavedAmount());
    assertFalse(createdChallenge.isCompleted());
  }

  @Test
  public void testUpdateChallenge() {
    Challenge challenge = new Challenge();
    challenge.setId(1L);
    when(challengeRepository.findById(any())).thenReturn(Optional.of(challenge));

    ConsumptionChallenge updatedChallenge = new ConsumptionChallenge();
    updatedChallenge.setDescription("New Description");

    when(challengeRepository.save(any())).thenReturn(challenge); // Add this line

    Challenge result = challengeService.updateChallenge(1L, updatedChallenge);

    assertNotNull(result);
    assertEquals("New Description", result.getDescription());
  }

  @Test
  public void testAddToSavedAmount() {
    Challenge challenge = new Challenge();
    challenge.setId(1L);
    challenge.setSavedAmount(50.0);
    challenge.setTargetAmount(100.0);
    when(challengeRepository.findById(any())).thenReturn(Optional.of(challenge));

    challengeService.addToSavedAmount(1L, 25.0);

    assertEquals(75.0, challenge.getSavedAmount());
    assertFalse(challenge.isCompleted());

    challengeService.addToSavedAmount(1L, 30.0); // Change this line

    assertEquals(105.0, challenge.getSavedAmount()); // And this line
    assertTrue(challenge.isCompleted());
  }

  // Add more tests for deleteChallenge, getAll, getById, addToSavedAmount
}
