package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.model.Badge;
import edu.ntnu.idi.stud.team10.sparesti.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class BadgeDtoTest {

  private BadgeDto badgeDto;

  @BeforeEach
  public void setUp() {
    badgeDto =
        new BadgeDto(
            1,
            "Test Badge",
            "This is a test badge",
            "https://example.com/test-badge.png",
            new HashSet<User>(Set.of(mock(User.class))));
  }

  @Test
  public void testConstructor() {
    assertNotNull(badgeDto);
    assertEquals(1, badgeDto.getId());
    assertEquals("Test Badge", badgeDto.getTitle());
    assertEquals("This is a test badge", badgeDto.getDescription());
    assertEquals("https://example.com/test-badge.png", badgeDto.getImageUrl());
    assertEquals(1, badgeDto.getUsers().size()); // Ensure users are copied correctly
  }

  @Test
  public void testToEntity() {
    Badge convertedBadge = badgeDto.toEntity();
    assertNotNull(convertedBadge);
    assertEquals(1, convertedBadge.getId());
    assertEquals("Test Badge", convertedBadge.getTitle());
    assertEquals("This is a test badge", convertedBadge.getDescription());
    assertEquals("https://example.com/test-badge.png", convertedBadge.getImageUrl());
    assertEquals(1, convertedBadge.getUsers().size()); // Ensure users are copied correctly
  }
}
