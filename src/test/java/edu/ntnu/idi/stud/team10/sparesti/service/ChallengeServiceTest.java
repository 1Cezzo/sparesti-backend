package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;
import edu.ntnu.idi.stud.team10.sparesti.model.Challenge;
import edu.ntnu.idi.stud.team10.sparesti.model.ConsumptionChallenge;
import edu.ntnu.idi.stud.team10.sparesti.repository.ChallengeRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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
}
