package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class UserChallengeServiceTest {

  @Mock private ChallengeRepository<Challenge> challengeRepository;

  @Mock private UserRepository userRepository;

  @Mock private UserInfoService userInfoService;

  @Mock private ChatGPTService chatGPTService;

  private UserChallengeService<Challenge> userChallengeService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    userChallengeService =
        new UserChallengeService<>(
            challengeRepository, userRepository, userInfoService, chatGPTService);
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
  public void testGetSortedChallengesByUser() {
    User user = new User();
    user.setChallenges(new ArrayList<>());

    // Create challenges with different completion status, expiry dates, and IDs
    ConsumptionChallenge completedChallenge = new ConsumptionChallenge();
    completedChallenge.setCompleted(true);
    completedChallenge.setExpiryDate(LocalDate.now().plusDays(1));
    completedChallenge.setId(1L);
    user.addChallenge(completedChallenge);

    PurchaseChallenge notCompletedChallenge = new PurchaseChallenge();
    notCompletedChallenge.setCompleted(false);
    notCompletedChallenge.setExpiryDate(LocalDate.now());
    notCompletedChallenge.setId(2L);
    user.addChallenge(notCompletedChallenge);

    SavingChallenge sameExpiryDateChallenge = new SavingChallenge();
    sameExpiryDateChallenge.setCompleted(false);
    sameExpiryDateChallenge.setExpiryDate(LocalDate.now());
    sameExpiryDateChallenge.setId(3L);
    user.addChallenge(sameExpiryDateChallenge);

    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

    List<ChallengeDto> result = userChallengeService.getSortedChallengesByUser(1L);

    assertNotNull(result);
    assertEquals(3, result.size());
    assertTrue(result.get(0) instanceof ConsumptionChallengeDto);
    assertTrue(result.get(1) instanceof PurchaseChallengeDto);
    assertTrue(result.get(2) instanceof SavingChallengeDto);
  }
}
