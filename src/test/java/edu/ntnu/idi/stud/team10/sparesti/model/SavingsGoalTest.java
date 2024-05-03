package edu.ntnu.idi.stud.team10.sparesti.model;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SavingsGoalTest {
  private SavingsGoal savingsGoal;

  @BeforeEach
  public void setUp() {
    savingsGoal = new SavingsGoal();
    savingsGoal.setId(1L);
    savingsGoal.setName("Test Savings Goal");
    savingsGoal.setTargetAmount(1000.0);
    savingsGoal.setSavedAmount(500.0);
    savingsGoal.setMediaUrl("https://example.com/image.jpg");
    savingsGoal.setDeadline(LocalDate.of(2023, 12, 31));
    savingsGoal.setCompleted(false);
    savingsGoal.setUsers(new ArrayList<>());
    savingsGoal.setAuthorId(1L);
  }

  @Nested
  class Constructors {
    @Test
    void noArgsConstructor() {
      SavingsGoal newSavingsGoal = new SavingsGoal();
      assertNotNull(newSavingsGoal);
    }

    @Test
    void allArgsConstructor() {
      SavingsGoal newSavingsGoal =
          new SavingsGoal(
              1L,
              "Test Savings Goal 2",
              2000.0,
              600.0,
              "https://example2.com/image.jpg",
              LocalDate.of(2024, 12, 31),
              false,
              new ArrayList<>(),
              2L);
      assertNotNull(newSavingsGoal);
    }
  }

  @Nested
  class GettersAndSetters {
    @Test
    void getAndSetId() {
      savingsGoal.setId(2L);
      assertEquals(2L, savingsGoal.getId());
    }

    @Test
    void getAndSetName() {
      savingsGoal.setName("Test Savings Goal 2");
      assertEquals("Test Savings Goal 2", savingsGoal.getName());
    }

    @Test
    void getAndSetTargetAmount() {
      savingsGoal.setTargetAmount(2000.0);
      assertEquals(2000.0, savingsGoal.getTargetAmount());
    }

    @Test
    void getAndSetSavedAmount() {
      savingsGoal.setSavedAmount(600.0);
      assertEquals(600.0, savingsGoal.getSavedAmount());
    }

    @Test
    void getAndSetMediaUrl() {
      savingsGoal.setMediaUrl("https://example2.com/image.jpg");
      assertEquals("https://example2.com/image.jpg", savingsGoal.getMediaUrl());
    }

    @Test
    void getAndSetDeadline() {
      LocalDate newDeadline = LocalDate.of(2024, 12, 31);
      savingsGoal.setDeadline(newDeadline);
      assertEquals(newDeadline, savingsGoal.getDeadline());
    }

    @Test
    void getAndSetCompleted() {
      savingsGoal.setCompleted(true);
      assertEquals(true, savingsGoal.isCompleted());
    }

    @Test
    void getAndSetUsers() {
      ArrayList<User> users = new ArrayList<>();
      savingsGoal.setUsers(users);
      assertEquals(users, savingsGoal.getUsers());
    }

    @Test
    void getAndSetAuthorId() {
      savingsGoal.setAuthorId(2L);
      assertEquals(2L, savingsGoal.getAuthorId());
    }
  }

  @Nested
  class EqualsAndHashcode {
    private SavingsGoal anotherSavingsGoal;

    @BeforeEach
    void setUp() {
      anotherSavingsGoal = new SavingsGoal();
      anotherSavingsGoal.setId(1L);
      anotherSavingsGoal.setName("Test Savings Goal");
      anotherSavingsGoal.setTargetAmount(1000.0);
      anotherSavingsGoal.setSavedAmount(500.0);
      anotherSavingsGoal.setMediaUrl("https://example.com/image.jpg");
      anotherSavingsGoal.setDeadline(LocalDate.of(2023, 12, 31));
      anotherSavingsGoal.setCompleted(false);
      anotherSavingsGoal.setUsers(new ArrayList<>());
      anotherSavingsGoal.setAuthorId(1L);
    }

    @Test
    void whenComparingSameData_thenObjectsAreEqual() {
      assertEquals(savingsGoal, anotherSavingsGoal);
    }

    @Test
    void whenComparingDifferentData_thenObjectsAreNotEqual() {
      anotherSavingsGoal.setName("Test Savings Goal 2");
      assertNotEquals(savingsGoal, anotherSavingsGoal);
    }

    @Test
    void whenComparingHashcodesOfSameData_thenHashcodesAreEqual() {
      assertEquals(savingsGoal.hashCode(), anotherSavingsGoal.hashCode());
    }

    @Test
    void whenComparingHashcodesOfDifferentData_thenHashcodesAreNotEqual() {
      anotherSavingsGoal.setName("Test Savings Goal 2");
      assertNotEquals(savingsGoal.hashCode(), anotherSavingsGoal.hashCode());
    }
  }

  @Test
  void toStringTest() {
    assertNotNull(savingsGoal.toString());
  }
}
