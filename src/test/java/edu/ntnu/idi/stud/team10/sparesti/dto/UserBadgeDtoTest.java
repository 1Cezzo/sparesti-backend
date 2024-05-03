package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserBadgeDtoTest {
  private UserBadgeDto userBadgeDto;

  @BeforeEach
  public void setUp() {
    userBadgeDto = new UserBadgeDto();
    userBadgeDto.setId(1L);
    userBadgeDto.setUserId(2L);
    userBadgeDto.setBadgeId(3L);
    userBadgeDto.setDateEarned(LocalDateTime.now());
  }

  @Nested
  class GettersAndSetters {
    @Test
    void getAndSetId() {
      userBadgeDto.setId(2L);
      assertEquals(2L, userBadgeDto.getId());
    }

    @Test
    void getAndSetUserId() {
      userBadgeDto.setUserId(3L);
      assertEquals(3L, userBadgeDto.getUserId());
    }

    @Test
    void getAndSetBadgeId() {
      userBadgeDto.setBadgeId(4L);
      assertEquals(4L, userBadgeDto.getBadgeId());
    }

    @Test
    void getAndSetDateEarned() {
      LocalDateTime testDate = LocalDateTime.now().plusDays(1);
      userBadgeDto.setDateEarned(testDate);
      assertEquals(testDate, userBadgeDto.getDateEarned());
    }
  }

  @Nested
  class EqualsAndHashcode {
    private UserBadgeDto anotherUserBadgeDto;

    @BeforeEach
    void setUp() {
      anotherUserBadgeDto = new UserBadgeDto();
      anotherUserBadgeDto.setId(1L);
      anotherUserBadgeDto.setUserId(2L);
      anotherUserBadgeDto.setBadgeId(3L);
      anotherUserBadgeDto.setDateEarned(LocalDateTime.now());
    }

    @Test
    void whenComparingSameData_thenObjectsAreEqual() {
      assertEquals(userBadgeDto, anotherUserBadgeDto);
    }

    @Test
    void whenComparingDifferentData_thenObjectsAreNotEqual() {
      anotherUserBadgeDto.setUserId(3L);
      assertNotEquals(userBadgeDto, anotherUserBadgeDto);
    }

    @Test
    void whenComparingHashcodesOfSameData_thenHashcodesAreEqual() {
      assertEquals(userBadgeDto.hashCode(), anotherUserBadgeDto.hashCode());
    }

    @Test
    void whenComparingHashcodesOfDifferentData_thenHashcodesAreNotEqual() {
      anotherUserBadgeDto.setUserId(3L);
      assertNotEquals(userBadgeDto.hashCode(), anotherUserBadgeDto.hashCode());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {2L, 3L})
    void differentIdReturnsFalse(Long id) {
      anotherUserBadgeDto.setId(id);
      assertNotEquals(userBadgeDto, anotherUserBadgeDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {3L, 4L})
    void differentUserIdReturnsFalse(Long userId) {
      anotherUserBadgeDto.setUserId(userId);
      assertNotEquals(userBadgeDto, anotherUserBadgeDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {4L, 5L})
    void differentBadgeIdReturnsFalse(Long badgeId) {
      anotherUserBadgeDto.setBadgeId(badgeId);
      assertNotEquals(userBadgeDto, anotherUserBadgeDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"2022-01-01T00:00:00", "2023-01-01T00:00:00"})
    void differentDateEarnedReturnsFalse(String dateEarned) {
      LocalDateTime date = dateEarned == null ? null : LocalDateTime.parse(dateEarned);
      anotherUserBadgeDto.setDateEarned(date);
      assertNotEquals(userBadgeDto, anotherUserBadgeDto);
    }
  }

  @Test
  void toStringTest() {
    assertNotNull(userBadgeDto.toString());
  }
}
