package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import edu.ntnu.idi.stud.team10.sparesti.dto.BadgeDto;
import edu.ntnu.idi.stud.team10.sparesti.model.Badge;
import edu.ntnu.idi.stud.team10.sparesti.repository.BadgeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BadgeServiceTest {

  @Mock private BadgeRepository badgeRepository;

  @Mock private UserRepository userRepository;

  private BadgeService badgeService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    badgeService = new BadgeService(badgeRepository, userRepository);
  }

  @Test
  public void testCreateBadge() {
    BadgeDto badgeDto = mock(BadgeDto.class); // Create a mock BadgeDto
    Badge badge = new Badge();
    when(badgeDto.toEntity()).thenReturn(badge);
    when(badgeRepository.save(any(Badge.class))).thenReturn(badge);

    BadgeDto result = badgeService.createBadge(badgeDto);

    assertNotNull(result);
    verify(badgeRepository, times(1)).save(badge);
  }

  @Test
  public void testDeleteBadgeById() {
    Badge badge = new Badge();
    badge.setUsers(new HashSet<>());
    when(badgeRepository.findById(any())).thenReturn(Optional.of(badge));

    assertDoesNotThrow(() -> badgeService.deleteBadgeById(1L));
    verify(badgeRepository, times(1)).findById(1L);
    verify(userRepository, times(1)).saveAll(badge.getUsers());
    verify(badgeRepository, times(1)).delete(badge);
  }

  @Test
  public void testGetAllBadges() {
    when(badgeRepository.findAll()).thenReturn(new ArrayList<>());

    List<Badge> result = badgeService.getAllBadges();

    assertNotNull(result);
    verify(badgeRepository, times(1)).findAll();
  }

  @Test
  public void testCountUsersWithBadge() {
    when(userRepository.countByBadgeId(anyLong())).thenReturn(1L); // Use anyLong() instead of any()

    long result = badgeService.countUsersWithBadge(1L);

    assertEquals(1L, result);
    verify(userRepository, times(1)).countByBadgeId(1L);
  }

  @Test
  public void testGetBadgeById() {
    Badge badge = new Badge();
    when(badgeRepository.findById(any())).thenReturn(Optional.of(badge));

    BadgeDto result = badgeService.getBadgeById(1L);

    assertNotNull(result);
    verify(badgeRepository, times(1)).findById(1L);
  }

  @Test
  public void testUpdateBadge() {
    Badge badge = new Badge();
    BadgeDto badgeDto = new BadgeDto();
    when(badgeRepository.findById(any())).thenReturn(Optional.of(badge));
    when(badgeRepository.save(any())).thenReturn(badge);

    BadgeDto result = badgeService.updateBadge(1L, badgeDto);

    assertNotNull(result);
    verify(badgeRepository, times(1)).findById(1L);
    verify(badgeRepository, times(1)).save(badge);
  }

  @Test
  public void testFindBadgeRarity() {
    when(userRepository.count()).thenReturn(1L);
    when(badgeRepository.findById(anyLong())).thenReturn(Optional.of(new Badge()));
    when(userRepository.countByBadgeId(anyLong())).thenReturn(1L);

    double result = badgeService.findBadgeRarity(1L);

    assertEquals(100.0, result, 0.01);
    verify(userRepository, times(2)).count(); // Verify that count() is called twice
    verify(badgeRepository, times(1)).findById(1L);
    verify(userRepository, times(1)).countByBadgeId(1L);
  }

  @Test
  public void testDeleteAllBadges() {
    assertDoesNotThrow(() -> badgeService.deleteAllBadges());
    verify(badgeRepository, times(1)).deleteAll();
  }
}
