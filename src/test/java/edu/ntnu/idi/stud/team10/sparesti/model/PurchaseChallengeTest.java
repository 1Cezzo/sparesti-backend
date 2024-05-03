package edu.ntnu.idi.stud.team10.sparesti.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PurchaseChallengeTest {
  private PurchaseChallenge purchaseChallenge;

  @BeforeEach
  public void setUp() {
    purchaseChallenge = new PurchaseChallenge();
    purchaseChallenge.setId(1L);
    purchaseChallenge.setProductName("Test Product");
    purchaseChallenge.setProductPrice(100.0);
  }

  @Nested
  class Constructors {
    @Test
    void noArgsConstructor() {
      PurchaseChallenge newPurchaseChallenge = new PurchaseChallenge();
      assertNotNull(newPurchaseChallenge);
    }

    @Test
    void allArgsConstructor() {
      PurchaseChallenge newPurchaseChallenge = new PurchaseChallenge("Test Product 2", 200.0);
      assertNotNull(newPurchaseChallenge);
    }
  }

  @Nested
  class GettersAndSetters {
    @Test
    void getAndSetId() {
      purchaseChallenge.setId(2L);
      assertEquals(2L, purchaseChallenge.getId());
    }

    @Test
    void getAndSetProductName() {
      purchaseChallenge.setProductName("Test Product 2");
      assertEquals("Test Product 2", purchaseChallenge.getProductName());
    }

    @Test
    void getAndSetProductPrice() {
      purchaseChallenge.setProductPrice(200.0);
      assertEquals(200.0, purchaseChallenge.getProductPrice());
    }
  }

  @Nested
  class EqualsAndHashcode {
    private PurchaseChallenge anotherPurchaseChallenge;

    @BeforeEach
    void setUp() {
      anotherPurchaseChallenge = new PurchaseChallenge();
      anotherPurchaseChallenge.setId(1L);
      anotherPurchaseChallenge.setProductName("Test Product");
      anotherPurchaseChallenge.setProductPrice(100.0);
    }

    @Test
    void whenComparingSameData_thenObjectsAreEqual() {
      assertEquals(purchaseChallenge, anotherPurchaseChallenge);
    }

    @Test
    void whenComparingDifferentData_thenObjectsAreNotEqual() {
      anotherPurchaseChallenge.setProductName("Test Product 2");
      assertNotEquals(purchaseChallenge, anotherPurchaseChallenge);
    }

    @Test
    void whenComparingHashcodesOfSameData_thenHashcodesAreEqual() {
      assertEquals(purchaseChallenge.hashCode(), anotherPurchaseChallenge.hashCode());
    }

    @Test
    void whenComparingHashcodesOfDifferentData_thenHashcodesAreNotEqual() {
      anotherPurchaseChallenge.setProductName("Test Product 2");
      assertNotEquals(purchaseChallenge.hashCode(), anotherPurchaseChallenge.hashCode());
    }
  }

  @Test
  void toStringTest() {
    assertNotNull(purchaseChallenge.toString());
  }
}
