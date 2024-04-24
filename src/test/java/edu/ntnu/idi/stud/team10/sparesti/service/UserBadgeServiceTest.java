package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.ntnu.idi.stud.team10.sparesti.dto.BadgeDto;
import edu.ntnu.idi.stud.team10.sparesti.model.Badge;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.repository.BadgeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserBadgeServiceTest {

  @Mock private BadgeRepository badgeRepository;

  @Mock private UserRepository userRepository;

  private UserBadgeService userBadgeService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    userBadgeService = new UserBadgeService(badgeRepository, userRepository);
  }

  @Test
  public void testGetAllBadgesByUserId() {
    User user = new User();
    user.setEarnedBadges(new HashSet<>());
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

    Set<BadgeDto> result = userBadgeService.getAllBadgesByUserId(1L);

    assertNotNull(result);
    verify(userRepository, times(1)).findById(1L);
  }

  @Test
  public void testGiveUserBadge() {
    User user = new User();
    user.setEarnedBadges(new HashSet<>());
    Badge badge = new Badge();
    badge.setUsers(new HashSet<>()); // Initialize the users set
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    when(badgeRepository.findById(anyLong())).thenReturn(Optional.of(badge));

    assertDoesNotThrow(() -> userBadgeService.giveUserBadge(1L, 1L));
    verify(userRepository, times(1)).findById(1L);
    verify(badgeRepository, times(1)).findById(1L);
    verify(userRepository, times(1)).save(user);
    verify(badgeRepository, times(1)).save(badge);
  }

  @Test
  public void testRemoveUserBadge() {
    User user = new User();
    user.setEarnedBadges(new HashSet<>());
    Badge badge = new Badge();
    badge.setUsers(new HashSet<>()); // Initialize the users set
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    when(badgeRepository.findById(anyLong())).thenReturn(Optional.of(badge));

    assertDoesNotThrow(() -> userBadgeService.removeUserBadge(1L, 1L));
    verify(userRepository, times(1)).findById(1L);
    verify(badgeRepository, times(1)).findById(1L);
    verify(userRepository, times(1)).save(user);
  }

  @Test
  public void testGetUsersByBadge() {
    Badge badge = new Badge();
    badge.setUsers(new HashSet<>());
    when(badgeRepository.findById(anyLong())).thenReturn(Optional.of(badge));

    List<User> result = userBadgeService.getUsersByBadge(1L);

    assertNotNull(result);
    verify(badgeRepository, times(1)).findById(1L);
  }
}
