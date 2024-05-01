package edu.ntnu.idi.stud.team10.sparesti.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BadgeDtoTest {

  private BadgeDto badgeDto;

  @BeforeEach
  public void setUp() {
    badgeDto =
        new BadgeDto(
            1L, "Test Badge", "This is a test badge", "https://example.com/test-badge.png");
  }

  @Test
  public void testConstructor() {
    assertNotNull(badgeDto);
    assertEquals(1, badgeDto.getId());
    assertEquals("Test Badge", badgeDto.getName());
    assertEquals("This is a test badge", badgeDto.getDescription());
    assertEquals("https://example.com/test-badge.png", badgeDto.getImageUrl());
  }
}
