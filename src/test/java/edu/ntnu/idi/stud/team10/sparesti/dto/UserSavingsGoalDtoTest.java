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

class UserSavingsGoalDtoTest {
  private UserSavingsGoalDto userSavingsGoalDto;
  private final LocalDateTime time = LocalDateTime.now();

  @BeforeEach
  public void setUp() {
    userSavingsGoalDto = new UserSavingsGoalDto();
    userSavingsGoalDto.setId(1L);
    userSavingsGoalDto.setUserId(2L);
    userSavingsGoalDto.setUserEmail("test@test.com");
    userSavingsGoalDto.setProfilePictureUrl("http://test.com/pic.jpg");
    userSavingsGoalDto.setSavingsGoalId(3L);
    userSavingsGoalDto.setSavingsGoalName("Test Goal");
    userSavingsGoalDto.setContributionAmount(100.0);
    userSavingsGoalDto.setLastContributed(time.toString());
  }

  @Nested
  class Constructors {
    @Test
    void customConstructor() {
      UserSavingsGoalDto dto =
          new UserSavingsGoalDto(1L, "email@test.com", "url", 100.0, LocalDateTime.now());
      assertNotNull(dto);
    }

    @Test
    void noArgsConstructor() {
      UserSavingsGoalDto dto = new UserSavingsGoalDto();
      assertNotNull(dto);
    }

    @Test
    void allArgsConstructor() {
      UserSavingsGoalDto dto =
          new UserSavingsGoalDto(
              1L, 2L, "email", "url", 3L, "name", 100.0, LocalDateTime.now().toString());
      assertNotNull(dto);
    }
  }

  @Nested
  class GettersAndSetters {
    @Test
    void getAndSetId() {
      userSavingsGoalDto.setId(2L);
      assertEquals(2L, userSavingsGoalDto.getId());
    }

    @Test
    void getAndSetUserId() {
      userSavingsGoalDto.setUserId(3L);
      assertEquals(3L, userSavingsGoalDto.getUserId());
    }

    @Test
    void getAndSetUserEmail() {
      userSavingsGoalDto.setUserEmail("test2@test.com");
      assertEquals("test2@test.com", userSavingsGoalDto.getUserEmail());
    }

    @Test
    void getAndSetProfilePictureUrl() {
      userSavingsGoalDto.setProfilePictureUrl("http://test2.com/pic.jpg");
      assertEquals("http://test2.com/pic.jpg", userSavingsGoalDto.getProfilePictureUrl());
    }

    @Test
    void getAndSetSavingsGoalId() {
      userSavingsGoalDto.setSavingsGoalId(4L);
      assertEquals(4L, userSavingsGoalDto.getSavingsGoalId());
    }

    @Test
    void getAndSetSavingsGoalName() {
      userSavingsGoalDto.setSavingsGoalName("Test Goal 2");
      assertEquals("Test Goal 2", userSavingsGoalDto.getSavingsGoalName());
    }

    @Test
    void getAndSetContributionAmount() {
      userSavingsGoalDto.setContributionAmount(200.0);
      assertEquals(200.0, userSavingsGoalDto.getContributionAmount());
    }

    @Test
    void getAndSetLastContributed() {
      String now = LocalDateTime.now().toString();
      userSavingsGoalDto.setLastContributed(now);
      assertEquals(now, userSavingsGoalDto.getLastContributed());
    }
  }

  @Nested
  class EqualsAndHashcode {
    private UserSavingsGoalDto anotherUserSavingsGoalDto;

    @BeforeEach
    void setUp() {
      anotherUserSavingsGoalDto = new UserSavingsGoalDto();
      anotherUserSavingsGoalDto.setId(1L);
      anotherUserSavingsGoalDto.setUserId(2L);
      anotherUserSavingsGoalDto.setUserEmail("test@test.com");
      anotherUserSavingsGoalDto.setProfilePictureUrl("http://test.com/pic.jpg");
      anotherUserSavingsGoalDto.setSavingsGoalId(3L);
      anotherUserSavingsGoalDto.setSavingsGoalName("Test Goal");
      anotherUserSavingsGoalDto.setContributionAmount(100.0);
      anotherUserSavingsGoalDto.setLastContributed(time.toString());
    }

    @Test
    void whenComparingSameData_thenObjectsAreEqual() {
      assertEquals(userSavingsGoalDto, anotherUserSavingsGoalDto);
    }

    @Test
    void whenComparingDifferentData_thenObjectsAreNotEqual() {
      anotherUserSavingsGoalDto.setUserId(3L);
      assertNotEquals(userSavingsGoalDto, anotherUserSavingsGoalDto);
    }

    @Test
    void whenComparingHashcodesOfSameData_thenHashcodesAreEqual() {
      assertEquals(userSavingsGoalDto.hashCode(), anotherUserSavingsGoalDto.hashCode());
    }

    @Test
    void whenComparingHashcodesOfDifferentData_thenHashcodesAreNotEqual() {
      anotherUserSavingsGoalDto.setUserId(3L);
      assertNotEquals(userSavingsGoalDto.hashCode(), anotherUserSavingsGoalDto.hashCode());
    }

    @ParameterizedTest
    @ValueSource(longs = {2L, 3L})
    void differentIdReturnsFalse(Long id) {
      anotherUserSavingsGoalDto.setId(id);
      assertNotEquals(userSavingsGoalDto, anotherUserSavingsGoalDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {3L, 4L})
    void differentUserIdReturnsFalse(Long userId) {
      anotherUserSavingsGoalDto.setUserId(userId);
      assertNotEquals(userSavingsGoalDto, anotherUserSavingsGoalDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"test2@test.com", "test3@test.com"})
    void differentUserEmailReturnsFalse(String userEmail) {
      anotherUserSavingsGoalDto.setUserEmail(userEmail);
      assertNotEquals(userSavingsGoalDto, anotherUserSavingsGoalDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"http://test2.com/pic.jpg", "http://test3.com/pic.jpg"})
    void differentProfilePictureUrlReturnsFalse(String profilePictureUrl) {
      anotherUserSavingsGoalDto.setProfilePictureUrl(profilePictureUrl);
      assertNotEquals(userSavingsGoalDto, anotherUserSavingsGoalDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {4L, 5L})
    void differentSavingsGoalIdReturnsFalse(Long savingsGoalId) {
      anotherUserSavingsGoalDto.setSavingsGoalId(savingsGoalId);
      assertNotEquals(userSavingsGoalDto, anotherUserSavingsGoalDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"Test Goal 2", "Test Goal 3"})
    void differentSavingsGoalNameReturnsFalse(String savingsGoalName) {
      anotherUserSavingsGoalDto.setSavingsGoalName(savingsGoalName);
      assertNotEquals(userSavingsGoalDto, anotherUserSavingsGoalDto);
    }

    @ParameterizedTest
    @ValueSource(doubles = {200.0, 300.0})
    void differentContributionAmountReturnsFalse(Double contributionAmount) {
      anotherUserSavingsGoalDto.setContributionAmount(contributionAmount);
      assertNotEquals(userSavingsGoalDto, anotherUserSavingsGoalDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"2022-01-01T00:00:00", "2023-01-01T00:00:00"})
    void differentLastContributedReturnsFalse(String lastContributed) {
      anotherUserSavingsGoalDto.setLastContributed(lastContributed);
      assertNotEquals(userSavingsGoalDto, anotherUserSavingsGoalDto);
    }
  }

  @Test
  void toStringTest() {
    assertNotNull(userSavingsGoalDto.toString());
  }
}
