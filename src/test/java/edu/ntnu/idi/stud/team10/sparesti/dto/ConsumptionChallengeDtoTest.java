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

class ConsumptionChallengeDtoTest {
  private ConsumptionChallengeDto consumptionChallengeDto;

  @BeforeEach
  public void setUp() {
    consumptionChallengeDto = new ConsumptionChallengeDto();
    consumptionChallengeDto.setId(1L);
    consumptionChallengeDto.setDescription("Test Consumption Challenge");
    consumptionChallengeDto.setTargetAmount(1000.0);
    consumptionChallengeDto.setProductCategory("Test Category");
    consumptionChallengeDto.setReductionPercentage(20.0);
  }

  @Nested
  class GettersAndSetters {
    @Test
    void getAndSetId() {
      consumptionChallengeDto.setId(2L);
      assertEquals(2L, consumptionChallengeDto.getId());
    }

    @Test
    void getAndSetDescription() {
      consumptionChallengeDto.setDescription("Test Consumption Challenge 2");
      assertEquals("Test Consumption Challenge 2", consumptionChallengeDto.getDescription());
    }

    @Test
    void getAndSetTargetAmount() {
      consumptionChallengeDto.setTargetAmount(2000.0);
      assertEquals(2000.0, consumptionChallengeDto.getTargetAmount());
    }

    @Test
    void getAndSetProductCategory() {
      consumptionChallengeDto.setProductCategory("Test Category 2");
      assertEquals("Test Category 2", consumptionChallengeDto.getProductCategory());
    }

    @Test
    void getAndSetReductionPercentage() {
      consumptionChallengeDto.setReductionPercentage(30.0);
      assertEquals(30.0, consumptionChallengeDto.getReductionPercentage());
    }
  }

  @Nested
  class EqualsAndHashcode {
    private ConsumptionChallengeDto anotherConsumptionChallengeDto;

    @BeforeEach
    void setUp() {
      anotherConsumptionChallengeDto = new ConsumptionChallengeDto();
      anotherConsumptionChallengeDto.setId(1L);
      anotherConsumptionChallengeDto.setDescription("Test Consumption Challenge");
      anotherConsumptionChallengeDto.setTargetAmount(1000.0);
      anotherConsumptionChallengeDto.setProductCategory("Test Category");
      anotherConsumptionChallengeDto.setReductionPercentage(20.0);
    }

    @Test
    void whenComparingSameData_thenObjectsAreEqual() {
      assertEquals(consumptionChallengeDto, anotherConsumptionChallengeDto);
    }

    @Test
    void whenComparingDifferentData_thenObjectsAreNotEqual() {
      anotherConsumptionChallengeDto.setDescription("Test Consumption Challenge 2");
      assertNotEquals(consumptionChallengeDto, anotherConsumptionChallengeDto);
    }

    @Test
    void whenComparingHashcodesOfSameData_thenHashcodesAreEqual() {
      assertEquals(consumptionChallengeDto.hashCode(), anotherConsumptionChallengeDto.hashCode());
    }

    @Test
    void whenComparingHashcodesOfDifferentData_thenHashcodesAreNotEqual() {
      anotherConsumptionChallengeDto.setDescription("Test Consumption Challenge 2");
      assertNotEquals(
          consumptionChallengeDto.hashCode(), anotherConsumptionChallengeDto.hashCode());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {2L, 3L})
    void differentIdReturnsFalse(Long id) {
      anotherConsumptionChallengeDto.setId(id);
      assertNotEquals(consumptionChallengeDto, anotherConsumptionChallengeDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"Test Consumption Challenge 2", "Test Consumption Challenge 3"})
    void differentDescriptionReturnsFalse(String description) {
      anotherConsumptionChallengeDto.setDescription(description);
      assertNotEquals(consumptionChallengeDto, anotherConsumptionChallengeDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(doubles = {2000.0, 3000.0})
    void differentTargetAmountReturnsFalse(Double targetAmount) {
      anotherConsumptionChallengeDto.setTargetAmount(targetAmount);
      assertNotEquals(consumptionChallengeDto, anotherConsumptionChallengeDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"Test Category 2", "Test Category 3"})
    void differentProductCategoryReturnsFalse(String productCategory) {
      anotherConsumptionChallengeDto.setProductCategory(productCategory);
      assertNotEquals(consumptionChallengeDto, anotherConsumptionChallengeDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(doubles = {30.0, 40.0})
    void differentReductionPercentageReturnsFalse(Double reductionPercentage) {
      anotherConsumptionChallengeDto.setReductionPercentage(reductionPercentage);
      assertNotEquals(consumptionChallengeDto, anotherConsumptionChallengeDto);
    }
  }

  @Test
  void toStringTest() {
    assertNotNull(consumptionChallengeDto.toString());
  }
}
