package edu.ntnu.idi.stud.team10.sparesti.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BadgeDtoTest {
  private BadgeDto badgeDto;

  @BeforeEach
  public void setUp() {
    badgeDto = new BadgeDto();
    badgeDto.setId(1L);
    badgeDto.setName("Test Badge");
    badgeDto.setDescription("Test Description");
    badgeDto.setImageUrl("https://example.com/image.jpg");
  }

  @Nested
  class GettersAndSetters {
    @Test
    void getAndSetId() {
      badgeDto.setId(2L);
      assertEquals(2L, badgeDto.getId());
    }

    @Test
    void getAndSetName() {
      badgeDto.setName("Test Badge 2");
      assertEquals("Test Badge 2", badgeDto.getName());
    }

    @Test
    void getAndSetDescription() {
      badgeDto.setDescription("Test Description 2");
      assertEquals("Test Description 2", badgeDto.getDescription());
    }

    @Test
    void getAndSetImageUrl() {
      badgeDto.setImageUrl("https://example2.com/image.jpg");
      assertEquals("https://example2.com/image.jpg", badgeDto.getImageUrl());
    }
  }

  @Nested
  class EqualsAndHashcode {
    private BadgeDto anotherBadgeDto;

    @BeforeEach
    void setUp() {
      anotherBadgeDto = new BadgeDto();
      anotherBadgeDto.setId(1L);
      anotherBadgeDto.setName("Test Badge");
      anotherBadgeDto.setDescription("Test Description");
      anotherBadgeDto.setImageUrl("https://example.com/image.jpg");
    }

    @Test
    void whenComparingSameData_thenObjectsAreEqual() {
      assertEquals(badgeDto, anotherBadgeDto);
    }

    @Test
    void whenComparingDifferentData_thenObjectsAreNotEqual() {
      anotherBadgeDto.setName("Test Badge 2");
      assertNotEquals(badgeDto, anotherBadgeDto);
    }

    @Test
    void whenComparingHashcodesOfSameData_thenHashcodesAreEqual() {
      assertEquals(badgeDto.hashCode(), anotherBadgeDto.hashCode());
    }

    @Test
    void whenComparingHashcodesOfDifferentData_thenHashcodesAreNotEqual() {
      anotherBadgeDto.setName("Test Badge 2");
      assertNotEquals(badgeDto.hashCode(), anotherBadgeDto.hashCode());
    }
  }

  @Test
  void toStringTest() {
    assertNotNull(badgeDto.toString());
  }
}
