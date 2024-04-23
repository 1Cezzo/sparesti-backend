package edu.ntnu.idi.stud.team10.sparesti.model;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BadgeTest {

  private Badge badge;

  @BeforeEach
  public void setUp() {
    badge = new Badge();
    badge.setId(1L);
    badge.setName("Test Badge");
    badge.setDescription("Test Description");
    badge.setImageUrl("https://example.com/badge.jpg");
    badge.setUsers(new HashSet<>());
  }

  @Test
  public void testBadgeFields() {
    assertEquals(1L, badge.getId());
    assertEquals("Test Badge", badge.getName());
    assertEquals("Test Description", badge.getDescription());
    assertEquals("https://example.com/badge.jpg", badge.getImageUrl());
    assertNotNull(badge.getUsers());
  }

  @Test
  public void testEqualsAndHashCode() {
    Badge sameBadge = new Badge();
    sameBadge.setId(1L);
    sameBadge.setName("Test Badge");
    sameBadge.setDescription("Test Description");
    sameBadge.setImageUrl("https://example.com/badge.jpg");
    sameBadge.setUsers(new HashSet<>());

    assertEquals(badge, sameBadge);
    assertEquals(badge.hashCode(), sameBadge.hashCode());

    Badge differentBadge = new Badge();
    differentBadge.setId(2L);
    differentBadge.setName("Test Badge");
    differentBadge.setDescription("Test Description");
    differentBadge.setImageUrl("https://example.com/badge.jpg");
    differentBadge.setUsers(new HashSet<>());

    assertNotEquals(badge, differentBadge);
    assertNotEquals(badge.hashCode(), differentBadge.hashCode());
  }

  @Test
  public void testToString() {
    String expectedString =
        "Badge(id=1, name=Test Badge, description=Test Description, imageUrl=https://example.com/badge.jpg, users=[])";
    assertEquals(expectedString, badge.toString());
  }
}
