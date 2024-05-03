package edu.ntnu.idi.stud.team10.sparesti.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConsumptionChallengeTest {
  private ConsumptionChallenge consumptionChallenge;

  @BeforeEach
  public void setUp() {
    consumptionChallenge = new ConsumptionChallenge();
    consumptionChallenge.setId(1L);
    consumptionChallenge.setProductCategory("Test Product Category");
    consumptionChallenge.setReductionPercentage(20.0);
  }

  @Nested
  class Constructors {
    @Test
    void noArgsConstructor() {
      ConsumptionChallenge newConsumptionChallenge = new ConsumptionChallenge();
      assertNotNull(newConsumptionChallenge);
    }

    @Test
    void allArgsConstructor() {
      ConsumptionChallenge newConsumptionChallenge =
          new ConsumptionChallenge("Test Product Category 2", 30.0);
      assertNotNull(newConsumptionChallenge);
    }
  }

  @Nested
  class GettersAndSetters {
    @Test
    void getAndSetId() {
      consumptionChallenge.setId(2L);
      assertEquals(2L, consumptionChallenge.getId());
    }

    @Test
    void getAndSetProductCategory() {
      consumptionChallenge.setProductCategory("Test Product Category 2");
      assertEquals("Test Product Category 2", consumptionChallenge.getProductCategory());
    }

    @Test
    void getAndSetReductionPercentage() {
      consumptionChallenge.setReductionPercentage(30.0);
      assertEquals(30.0, consumptionChallenge.getReductionPercentage());
    }
  }

  @Nested
  class EqualsAndHashcode {
    private ConsumptionChallenge anotherConsumptionChallenge;

    @BeforeEach
    void setUp() {
      anotherConsumptionChallenge = new ConsumptionChallenge();
      anotherConsumptionChallenge.setId(1L);
      anotherConsumptionChallenge.setProductCategory("Test Product Category");
      anotherConsumptionChallenge.setReductionPercentage(20.0);
    }

    @Test
    void whenComparingSameData_thenObjectsAreEqual() {
      assertEquals(consumptionChallenge, anotherConsumptionChallenge);
    }

    @Test
    void whenComparingDifferentData_thenObjectsAreNotEqual() {
      anotherConsumptionChallenge.setProductCategory("Test Product Category 2");
      assertNotEquals(consumptionChallenge, anotherConsumptionChallenge);
    }

    @Test
    void whenComparingHashcodesOfSameData_thenHashcodesAreEqual() {
      assertEquals(consumptionChallenge.hashCode(), anotherConsumptionChallenge.hashCode());
    }

    @Test
    void whenComparingHashcodesOfDifferentData_thenHashcodesAreNotEqual() {
      anotherConsumptionChallenge.setProductCategory("Test Product Category 2");
      assertNotEquals(consumptionChallenge.hashCode(), anotherConsumptionChallenge.hashCode());
    }
  }

  @Test
  void toStringTest() {
    assertNotNull(consumptionChallenge.toString());
  }
}
