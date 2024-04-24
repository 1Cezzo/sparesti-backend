package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.ntnu.idi.stud.team10.sparesti.dto.*;
import edu.ntnu.idi.stud.team10.sparesti.model.*;
import edu.ntnu.idi.stud.team10.sparesti.repository.ChallengeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserChallengeServiceTest {

  @Mock private ChallengeRepository<Challenge> challengeRepository;

  @Mock private UserRepository userRepository;

  private UserChallengeService<Challenge> userChallengeService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    userChallengeService = new UserChallengeService<>(challengeRepository, userRepository);
  }

  @Test
  public void testAddChallengeToUser() {
    User user = new User();
    user.setChallenges(new ArrayList<>()); // Add this line
    Challenge challenge = new Challenge();
    when(userRepository.findById(any())).thenReturn(Optional.of(user));
    when(challengeRepository.findById(any())).thenReturn(Optional.of(challenge));
    when(userRepository.save(any())).thenReturn(user);

    UserDto result = userChallengeService.addChallengeToUser(1L, 1L);

    assertNotNull(result);
    assertTrue(user.getChallenges().contains(challenge));
  }

  @Test
  public void testRemoveChallengeFromUser() {
    User user = new User();
    user.setChallenges(new ArrayList<>()); // Add this line
    Challenge challenge = new Challenge();
    user.addChallenge(challenge);
    when(userRepository.findById(any())).thenReturn(Optional.of(user));
    when(challengeRepository.findById(any())).thenReturn(Optional.of(challenge));
    when(userRepository.save(any())).thenReturn(user);

    UserDto result = userChallengeService.removeChallengeFromUser(1L, 1L);

    assertNotNull(result);
    assertFalse(user.getChallenges().contains(challenge));
  }

  @Test
  public void testGetChallengesByUser() {
    User user = new User();
    user.setChallenges(new ArrayList<>()); // Add this line
    Challenge challenge = new Challenge();
    user.addChallenge(challenge);
    when(userRepository.findById(any())).thenReturn(Optional.of(user));

    Map<String, List<? extends ChallengeDTO>> result = userChallengeService.getChallengesByUser(1L);

    assertNotNull(result);
    assertTrue(result.get("consumptionChallenges").isEmpty());
    assertTrue(result.get("purchaseChallenges").isEmpty());
    assertTrue(result.get("savingChallenges").isEmpty());
  }
}
