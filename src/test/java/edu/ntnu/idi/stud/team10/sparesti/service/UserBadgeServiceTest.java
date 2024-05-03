package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.mapper.UserMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.Badge;
import edu.ntnu.idi.stud.team10.sparesti.model.User;
import edu.ntnu.idi.stud.team10.sparesti.model.UserBadge;
import edu.ntnu.idi.stud.team10.sparesti.repository.BadgeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserBadgeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserBadgeServiceTest {

  @Mock private BadgeRepository badgeRepository;

  @Mock private UserRepository userRepository;

  @Mock private UserBadgeRepository userBadgeRepository;

  @Mock private UserMapper userMapper;

  @InjectMocks private UserBadgeService userBadgeService;

  @BeforeEach
  public void setUp() {
    when(userMapper.toDto(any(User.class))).thenReturn(new UserDto());
    when(userMapper.toEntity(any(UserDto.class))).thenReturn(new User());
  }

  @Test
  public void testGetAllBadgesByUserId() {
    UserBadge userBadge = new UserBadge();
    userBadge.setUser(new User());
    userBadge.setBadge(new Badge());
    userBadge.setDateEarned(LocalDateTime.now());

    Set<UserBadge> userBadges = new HashSet<>();
    userBadges.add(userBadge);

    when(userBadgeRepository.findByUserId(anyLong())).thenReturn(userBadges);

    Set<Map<String, Object>> result = userBadgeService.getAllBadgesByUserId(1L);

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(userBadgeRepository, times(1)).findByUserId(1L);
  }

  @Test
  public void testGiveUserBadge() {
    User user = new User();
    Badge badge = new Badge();

    when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
    when(badgeRepository.findById(anyLong())).thenReturn(Optional.of(badge));

    assertDoesNotThrow(() -> userBadgeService.giveUserBadge(1L, 1L));
    verify(userRepository, times(1)).findById(1L);
    verify(badgeRepository, times(1)).findById(1L);
    verify(userBadgeRepository, times(1)).save(any(UserBadge.class));
  }

  @Test
  public void testRemoveUserBadge() {
    UserBadge userBadge = new UserBadge();

    when(userBadgeRepository.findByUserIdAndBadgeId(anyLong(), anyLong()))
        .thenReturn(Optional.of(userBadge));

    assertDoesNotThrow(() -> userBadgeService.removeUserBadge(1L, 1L));
    verify(userBadgeRepository, times(1)).findByUserIdAndBadgeId(1L, 1L);
    verify(userBadgeRepository, times(1)).delete(userBadge);
  }

  @Test
  public void testGetUsersByBadge() {
    UserBadge userBadge = new UserBadge();
    userBadge.setUser(new User());
    userBadge.setBadge(new Badge());
    userBadge.setDateEarned(LocalDateTime.now());

    List<UserBadge> userBadges = new ArrayList<>();
    userBadges.add(userBadge);

    when(userBadgeRepository.findByBadgeId(anyLong())).thenReturn(userBadges);

    List<Map<String, Object>> result = userBadgeService.getUsersByBadge(1L);

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(userBadgeRepository, times(1)).findByBadgeId(1L);
  }
}
