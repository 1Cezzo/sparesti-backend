package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import edu.ntnu.idi.stud.team10.sparesti.dto.*;
import edu.ntnu.idi.stud.team10.sparesti.mapper.ChallengeMapper;
import edu.ntnu.idi.stud.team10.sparesti.mapper.UserMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.*;
import edu.ntnu.idi.stud.team10.sparesti.repository.ChallengeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserChallengeServiceTest {

  @Mock private ChallengeRepository<Challenge> challengeRepository;

  @Mock private UserRepository userRepository;

  @Mock private UserInfoService userInfoService;

  @Mock private ChatGPTService chatGPTService;

  @Mock private UserMapper userMapper;

  @Mock private ChallengeMapper challengeMapper;

  @InjectMocks private UserChallengeService<Challenge> userChallengeService;

  @BeforeEach
  public void setUp() {
    when(userMapper.toDto(any(User.class))).thenReturn(new UserDto());
    when(userMapper.toEntity(any(UserDto.class))).thenReturn(new User());
  }

  @Test
  void testAddChallengeToUser() {
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
  void testGetSortedChallengesByUser() {
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
    when(challengeMapper.toDto(completedChallenge))
        .thenReturn(ChallengeMapper.INSTANCE.toDto(completedChallenge));
    when(challengeMapper.toDto(notCompletedChallenge))
        .thenReturn(ChallengeMapper.INSTANCE.toDto(notCompletedChallenge));
    when(challengeMapper.toDto(sameExpiryDateChallenge))
        .thenReturn(ChallengeMapper.INSTANCE.toDto(sameExpiryDateChallenge));

    List<ChallengeDto> result = userChallengeService.getSortedChallengesByUser(1L);

    assertNotNull(result);
    assertEquals(3, result.size());
    assertInstanceOf(ConsumptionChallengeDto.class, result.get(0));
    assertInstanceOf(PurchaseChallengeDto.class, result.get(1));
    assertInstanceOf(SavingChallengeDto.class, result.get(2));
  }
}
