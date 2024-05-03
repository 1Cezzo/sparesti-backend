package edu.ntnu.idi.stud.team10.sparesti.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserSavingsGoalTest {
  private UserSavingsGoal userSavingsGoal;
  private User user;
  private SavingsGoal savingsGoal;

  @BeforeEach
  public void setUp() {
    userSavingsGoal = new UserSavingsGoal();
    userSavingsGoal.setLastContributed(LocalDateTime.now());
    userSavingsGoal.setContributionAmount(500.0);
    user = new User();
    user.setId(1L);
    user.setPassword("testPassword");
    user.setEmail("test@example.com");
    user.setProfilePictureUrl("https://example.com/profile.jpg");
    userSavingsGoal.setUser(user);
    savingsGoal = new SavingsGoal();
    savingsGoal.setId(1L);
    savingsGoal.setName("Test Savings Goal");
    savingsGoal.setTargetAmount(1000.0);
    savingsGoal.setSavedAmount(500.0);
    savingsGoal.setMediaUrl("https://example.com/image.jpg");
    savingsGoal.setDeadline(LocalDate.now().plusYears(1));
    savingsGoal.setCompleted(false);
    savingsGoal.setUsers(new ArrayList<>());
    savingsGoal.setAuthorId(1L);
    userSavingsGoal.setSavingsGoal(savingsGoal);
  }

  @Nested
  class GettersAndSetters {
    @Test
    void getAndSetLastContributed() {
      LocalDateTime newLastContributed = LocalDateTime.now().plusDays(1);
      userSavingsGoal.setLastContributed(newLastContributed);
      assertEquals(newLastContributed, userSavingsGoal.getLastContributed());
    }

    @Test
    void getAndSetContributionAmount() {
      userSavingsGoal.setContributionAmount(600.0);
      assertEquals(600.0, userSavingsGoal.getContributionAmount());
    }

    @Test
    void getAndSetUser() {
      User newUser = new User();
      newUser.setId(2L);
      newUser.setPassword("newPassword");
      newUser.setEmail("new@example.com");
      newUser.setProfilePictureUrl("https://example2.com/profile.jpg");
      userSavingsGoal.setUser(newUser);
      assertEquals(newUser, userSavingsGoal.getUser());
    }

    @Test
    void getAndSetSavingsGoal() {
      SavingsGoal newSavingsGoal = new SavingsGoal();
      newSavingsGoal.setId(2L);
      newSavingsGoal.setName("Test Savings Goal 2");
      newSavingsGoal.setTargetAmount(2000.0);
      newSavingsGoal.setSavedAmount(600.0);
      newSavingsGoal.setMediaUrl("https://example2.com/image.jpg");
      newSavingsGoal.setDeadline(LocalDate.now().plusYears(2));
      newSavingsGoal.setCompleted(false);
      newSavingsGoal.setUsers(new ArrayList<>());
      newSavingsGoal.setAuthorId(2L);
      userSavingsGoal.setSavingsGoal(newSavingsGoal);
      assertEquals(newSavingsGoal, userSavingsGoal.getSavingsGoal());
    }
  }

  @Nested
  class EqualsAndHashcode {
    private UserSavingsGoal anotherUserSavingsGoal;

    @BeforeEach
    void setUp() {
      anotherUserSavingsGoal = new UserSavingsGoal();
      anotherUserSavingsGoal.setLastContributed(LocalDateTime.now());
      anotherUserSavingsGoal.setContributionAmount(500.0);
      User anotherUser = new User();
      anotherUser.setId(1L);
      anotherUser.setPassword("testPassword");
      anotherUser.setEmail("test@example.com");
      anotherUser.setProfilePictureUrl("https://example.com/profile.jpg");
      anotherUserSavingsGoal.setUser(anotherUser);
      SavingsGoal anotherSavingsGoal = new SavingsGoal();
      anotherSavingsGoal.setId(1L);
      anotherSavingsGoal.setName("Test Savings Goal");
      anotherSavingsGoal.setTargetAmount(1000.0);
      anotherSavingsGoal.setSavedAmount(500.0);
      anotherSavingsGoal.setMediaUrl("https://example.com/image.jpg");
      anotherSavingsGoal.setDeadline(LocalDate.now().plusYears(1));
      anotherSavingsGoal.setCompleted(false);
      anotherSavingsGoal.setUsers(new ArrayList<>());
      anotherSavingsGoal.setAuthorId(1L);
      anotherUserSavingsGoal.setSavingsGoal(anotherSavingsGoal);
    }

    @Test
    void whenComparingSameData_thenObjectsAreEqual() {
      assertEquals(userSavingsGoal, anotherUserSavingsGoal);
    }

    @Test
    void whenComparingDifferentData_thenObjectsAreNotEqual() {
      anotherUserSavingsGoal.setContributionAmount(600.0);
      assertNotEquals(userSavingsGoal, anotherUserSavingsGoal);
    }

    @Test
    void whenComparingHashcodesOfSameData_thenHashcodesAreEqual() {
      assertEquals(userSavingsGoal.hashCode(), anotherUserSavingsGoal.hashCode());
    }

    @Test
    void whenComparingHashcodesOfDifferentData_thenHashcodesAreNotEqual() {
      anotherUserSavingsGoal.setContributionAmount(600.0);
      assertNotEquals(userSavingsGoal.hashCode(), anotherUserSavingsGoal.hashCode());
    }
  }

  @Test
  void toStringTest() {
    assertNotNull(userSavingsGoal.toString());
  }
}
