package edu.ntnu.idi.stud.team10.sparesti.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PurchaseChallengeDtoTest {
  private PurchaseChallengeDto purchaseChallengeDto;

  @BeforeEach
  public void setUp() {
    purchaseChallengeDto = new PurchaseChallengeDto();
    purchaseChallengeDto.setId(1L);
    purchaseChallengeDto.setProductName("Test Product");
    purchaseChallengeDto.setProductPrice(100.0);
  }

  @Nested
  class GettersAndSetters {
    @Test
    void getAndSetId() {
      purchaseChallengeDto.setId(2L);
      assertEquals(2L, purchaseChallengeDto.getId());
    }

    @Test
    void getAndSetProductName() {
      purchaseChallengeDto.setProductName("Test Product 2");
      assertEquals("Test Product 2", purchaseChallengeDto.getProductName());
    }

    @Test
    void getAndSetProductPrice() {
      purchaseChallengeDto.setProductPrice(200.0);
      assertEquals(200.0, purchaseChallengeDto.getProductPrice());
    }
  }

  @Nested
  class EqualsAndHashcode {
    private PurchaseChallengeDto anotherPurchaseChallengeDto;

    @BeforeEach
    void setUp() {
      anotherPurchaseChallengeDto = new PurchaseChallengeDto();
      anotherPurchaseChallengeDto.setId(1L);
      anotherPurchaseChallengeDto.setProductName("Test Product");
      anotherPurchaseChallengeDto.setProductPrice(100.0);
    }

    @Test
    void whenComparingSameData_thenObjectsAreEqual() {
      assertEquals(purchaseChallengeDto, anotherPurchaseChallengeDto);
    }

    @Test
    void whenComparingDifferentData_thenObjectsAreNotEqual() {
      anotherPurchaseChallengeDto.setProductName("Test Product 2");
      assertNotEquals(purchaseChallengeDto, anotherPurchaseChallengeDto);
    }

    @Test
    void whenComparingHashcodesOfSameData_thenHashcodesAreEqual() {
      assertEquals(purchaseChallengeDto.hashCode(), anotherPurchaseChallengeDto.hashCode());
    }

    @Test
    void whenComparingHashcodesOfDifferentData_thenHashcodesAreNotEqual() {
      anotherPurchaseChallengeDto.setProductName("Test Product 2");
      assertNotEquals(purchaseChallengeDto.hashCode(), anotherPurchaseChallengeDto.hashCode());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {2L, 3L})
    void differentIdReturnsFalse(Long id) {
      anotherPurchaseChallengeDto.setId(id);
      assertNotEquals(purchaseChallengeDto, anotherPurchaseChallengeDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"Test Product 2", "Test Product 3"})
    void differentProductNameReturnsFalse(String productName) {
      anotherPurchaseChallengeDto.setProductName(productName);
      assertNotEquals(purchaseChallengeDto, anotherPurchaseChallengeDto);
    }

    @ParameterizedTest
    @ValueSource(doubles = {200.0, 300.0})
    void differentProductPriceReturnsFalse(Double productPrice) {
      anotherPurchaseChallengeDto.setProductPrice(productPrice);
      assertNotEquals(purchaseChallengeDto, anotherPurchaseChallengeDto);
    }
  }

  @Test
  void toStringTest() {
    assertNotNull(purchaseChallengeDto.toString());
  }
}
