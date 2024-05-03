package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import edu.ntnu.idi.stud.team10.sparesti.model.Badge;
import edu.ntnu.idi.stud.team10.sparesti.repository.BadgeRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BadgeAwarderTest {

  @Mock private UserBadgeService userBadgeService;
  @Mock private BadgeRepository badgeRepository;
  @Mock private UserChallengeService userChallengeService;
  @Mock private SavingsGoalService savingsGoalService;
  @Mock private UserService userService;
  @Mock private BudgetService budgetService;

  @InjectMocks private BadgeAwarder badgeAwarder;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(badgeAwarder, "loginBadgeName", "På god vei!");
    ReflectionTestUtils.setField(badgeAwarder, "threeChallengesBadgeName", "3 Utfordringer!");
    ReflectionTestUtils.setField(badgeAwarder, "fiveChallengesBadgeName", "10 Utfordringer!");
    ReflectionTestUtils.setField(badgeAwarder, "fifteenChallengesBadgeName", "15 Utfordringer!");
    ReflectionTestUtils.setField(badgeAwarder, "savingsGoalCompletedBadgeName", "Sparemål oppnådd");
    ReflectionTestUtils.setField(badgeAwarder, "sharedSavingsGoalBadgeName", "Delt sparemål");
    ReflectionTestUtils.setField(badgeAwarder, "profilePictureBadgeName", "Profilbilde");
    ReflectionTestUtils.setField(badgeAwarder, "budgetBadgeName", "Budsjett");
    ReflectionTestUtils.setField(badgeAwarder, "savingsGoalBadgeName", "Sparemål nybegynner");
    ReflectionTestUtils.setField(
        badgeAwarder, "challengeCreationBadgeName", "Utfordrings nybegynner");
  }

  @Test
  void testCheckAndAwardLoginBadge_UserHasBadge_ReturnsNull() {
    // Arrange
    Long userId = 1L;
    Badge loginBadge = new Badge();
    loginBadge.setId(1L);
    when(badgeRepository.findByName("På god vei!")).thenReturn(Optional.of(loginBadge));
    when(userBadgeService.hasUserBadge(userId, loginBadge.getId())).thenReturn(true);

    // Act
    Badge result = badgeAwarder.checkAndAwardLoginBadge(userId);

    // Assert
    assertNull(result);
  }

  @Test
  void testCheckAndAwardThreeChallengesBadge_UserHasBadge_ReturnsNull() {
    // Arrange
    Long userId = 1L;
    Badge threeChallengesBadge = new Badge();
    threeChallengesBadge.setId(2L);
    when(badgeRepository.findByName("3 Utfordringer!"))
        .thenReturn(Optional.of(threeChallengesBadge));
    when(userBadgeService.hasUserBadge(userId, threeChallengesBadge.getId())).thenReturn(true);

    // Act
    Badge result = badgeAwarder.checkAndAwardChallengeBadge(userId, "3 Utfordringer!", 3);

    // Assert
    assertNull(result);
  }

  @Test
  void testCheckAndAwardTenChallengesBadge_UserHasBadge_ReturnsNull() {
    // Arrange
    Long userId = 1L;
    Badge fiveChallengesBadge = new Badge();
    fiveChallengesBadge.setId(3L);
    when(badgeRepository.findByName("10 Utfordringer!"))
        .thenReturn(Optional.of(fiveChallengesBadge));
    when(userBadgeService.hasUserBadge(userId, fiveChallengesBadge.getId())).thenReturn(true);

    // Act
    Badge result = badgeAwarder.checkAndAwardChallengeBadge(userId, "10 Utfordringer!", 10);

    // Assert
    assertNull(result);
  }

  @Test
  void testCheckAndAwardChallengeCreationBadge_UserHasBadge_ReturnsNull() {
    // Arrange
    Long userId = 1L;
    Badge challengeCreationBadge = new Badge();
    challengeCreationBadge.setId(4L);
    when(badgeRepository.findByName("Utfordrings nybegynner"))
        .thenReturn(Optional.of(challengeCreationBadge));
    when(userBadgeService.hasUserBadge(userId, challengeCreationBadge.getId())).thenReturn(true);

    // Act
    Badge result = badgeAwarder.checkAndAwardChallengeCreationBadge(userId);

    // Assert
    assertNull(result);
  }

  @Test
  void testCheckAndAwardSavingsGoalBadge_UserHasBadge_ReturnsNull() {
    // Arrange
    Long userId = 1L;
    Badge savingsGoalBadge = new Badge();
    savingsGoalBadge.setId(5L);
    when(badgeRepository.findByName("Sparemål nybegynner"))
        .thenReturn(Optional.of(savingsGoalBadge));
    when(userBadgeService.hasUserBadge(userId, savingsGoalBadge.getId())).thenReturn(true);

    // Act
    Badge result = badgeAwarder.checkAndAwardSavingsGoalBadge(userId);

    // Assert
    assertNull(result);
  }

  @Test
  void testCheckAndAwardSavingsGoalCompletedBadge_UserHasBadge_ReturnsNull() {
    // Arrange
    Long userId = 1L;
    Badge savingsGoalCompletedBadge = new Badge();
    savingsGoalCompletedBadge.setId(6L);
    when(badgeRepository.findByName("Sparemål oppnådd"))
        .thenReturn(Optional.of(savingsGoalCompletedBadge));
    when(userBadgeService.hasUserBadge(userId, savingsGoalCompletedBadge.getId())).thenReturn(true);

    // Act
    Badge result = badgeAwarder.checkAndAwardCompletedSavingsGoalBadge(userId);

    // Assert
    assertNull(result);
  }

  @Test
  void testCheckAndAwardSharedSavingsGoalBadge_UserHasBadge_ReturnsNull() {
    // Arrange
    Long userId = 1L;
    Badge sharedSavingsGoalBadge = new Badge();
    sharedSavingsGoalBadge.setId(7L);
    when(badgeRepository.findByName("Delt sparemål"))
        .thenReturn(Optional.of(sharedSavingsGoalBadge));
    when(userBadgeService.hasUserBadge(userId, sharedSavingsGoalBadge.getId())).thenReturn(true);

    // Act
    Badge result = badgeAwarder.checkAndAwardSharedSavingsGoalBadge(userId);

    // Assert
    assertNull(result);
  }

  @Test
  void testCheckAndAwardProfilePictureBadge_UserHasBadge_ReturnsNull() {
    // Arrange
    Long userId = 1L;
    Badge profilePictureBadge = new Badge();
    profilePictureBadge.setId(8L);
    when(badgeRepository.findByName("Profilbilde")).thenReturn(Optional.of(profilePictureBadge));
    when(userBadgeService.hasUserBadge(userId, profilePictureBadge.getId())).thenReturn(true);

    // Act
    Badge result = badgeAwarder.checkAndAwardProfilePictureBadge(userId);

    // Assert
    assertNull(result);
  }

  @Test
  void testCheckAndAwardBudgetBadge_UserHasBadge_ReturnsNull() {
    // Arrange
    Long userId = 1L;
    Badge budgetBadge = new Badge();
    budgetBadge.setId(9L);
    when(badgeRepository.findByName("Budsjett")).thenReturn(Optional.of(budgetBadge));
    when(userBadgeService.hasUserBadge(userId, budgetBadge.getId())).thenReturn(true);

    // Act
    Badge result = badgeAwarder.checkAndAwardBudgetBadge(userId);

    // Assert
    assertNull(result);
  }
}
