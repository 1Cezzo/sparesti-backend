package edu.ntnu.idi.stud.team10.sparesti.model;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserBadgeTest {
  private UserBadge userBadge;
  private User user;
  private Badge badge;
  private final LocalDateTime dateEarned = LocalDateTime.now();

  @BeforeEach
  public void setUp() {
    userBadge = new UserBadge();
    userBadge.setDateEarned(dateEarned);
    user = new User();
    user.setId(1L);
    user.setPassword("testPassword");
    user.setEmail("test@example.com");
    user.setProfilePictureUrl("https://example.com/profile.jpg");
    userBadge.setUser(user);
    badge = new Badge();
    badge.setId(1L);
    badge.setName("Test Badge");
    badge.setDescription("Test Badge Description");
    userBadge.setBadge(badge);
  }

  @Nested
  class GettersAndSetters {
    @Test
    void getAndSetDateEarned() {
      LocalDateTime newDateEarned = LocalDateTime.now().plusDays(1);
      userBadge.setDateEarned(newDateEarned);
      assertEquals(newDateEarned, userBadge.getDateEarned());
    }

    @Test
    void getAndSetUser() {
      User newUser = new User();
      newUser.setId(2L);
      newUser.setPassword("newPassword");
      newUser.setEmail("new@example.com");
      newUser.setProfilePictureUrl("https://example2.com/profile.jpg");
      userBadge.setUser(newUser);
      assertEquals(newUser, userBadge.getUser());
    }

    @Test
    void getAndSetBadge() {
      Badge newBadge = new Badge();
      newBadge.setId(2L);
      newBadge.setName("Test Badge 2");
      newBadge.setDescription("Test Badge Description 2");
      userBadge.setBadge(newBadge);
      assertEquals(newBadge, userBadge.getBadge());
    }
  }

  @Nested
  class EqualsAndHashcode {
    private UserBadge anotherUserBadge;

    @BeforeEach
    void setUp() {
      anotherUserBadge = new UserBadge();
      anotherUserBadge.setDateEarned(dateEarned);
      User anotherUser = new User();
      anotherUser.setId(1L);
      anotherUser.setPassword("testPassword");
      anotherUser.setEmail("test@example.com");
      anotherUser.setProfilePictureUrl("https://example.com/profile.jpg");
      anotherUserBadge.setUser(anotherUser);
      Badge anotherBadge = new Badge();
      anotherBadge.setId(1L);
      anotherBadge.setName("Test Badge");
      anotherBadge.setDescription("Test Badge Description");
      anotherUserBadge.setBadge(anotherBadge);
    }

    @Test
    void whenComparingSameData_thenObjectsAreEqual() {
      assertEquals(userBadge, anotherUserBadge);
    }

    @Test
    void whenComparingDifferentData_thenObjectsAreNotEqual() {
      anotherUserBadge.setDateEarned(LocalDateTime.now().plusDays(1));
      assertNotEquals(userBadge, anotherUserBadge);
    }

    @Test
    void whenComparingHashcodesOfSameData_thenHashcodesAreEqual() {
      assertEquals(userBadge.hashCode(), anotherUserBadge.hashCode());
    }

    @Test
    void whenComparingHashcodesOfDifferentData_thenHashcodesAreNotEqual() {
      anotherUserBadge.setDateEarned(LocalDateTime.now().plusDays(1));
      assertNotEquals(userBadge.hashCode(), anotherUserBadge.hashCode());
    }
  }

  @Test
  void toStringTest() {
    assertNotNull(userBadge.toString());
  }
}
