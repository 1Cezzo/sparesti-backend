package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SavingChallengeDtoTest {
  private SavingChallengeDto savingChallengeDto;

  @BeforeEach
  public void setUp() {
    savingChallengeDto = new SavingChallengeDto();
    savingChallengeDto.setId(1L);
    savingChallengeDto.setDescription("Test Saving Challenge");
    savingChallengeDto.setTargetAmount(1000.0);
    savingChallengeDto.setUsedAmount(500.0);
    savingChallengeDto.setMediaUrl("https://example.com/image.jpg");
    savingChallengeDto.setTimeInterval(TimeInterval.MONTHLY);
    savingChallengeDto.setDifficultyLevel(DifficultyLevel.MEDIUM);
    savingChallengeDto.setExpiryDate(LocalDate.now().plusMonths(1));
    savingChallengeDto.setCompleted(false);
  }

  @Nested
  class GettersAndSetters {
    @Test
    void getAndSetId() {
      savingChallengeDto.setId(2L);
      assertEquals(2L, savingChallengeDto.getId());
    }

    @Test
    void getAndSetDescription() {
      savingChallengeDto.setDescription("Test Saving Challenge 2");
      assertEquals("Test Saving Challenge 2", savingChallengeDto.getDescription());
    }

    @Test
    void getAndSetTargetAmount() {
      savingChallengeDto.setTargetAmount(2000.0);
      assertEquals(2000.0, savingChallengeDto.getTargetAmount());
    }

    @Test
    void getAndSetUsedAmount() {
      savingChallengeDto.setUsedAmount(1000.0);
      assertEquals(1000.0, savingChallengeDto.getUsedAmount());
    }

    @Test
    void getAndSetMediaUrl() {
      savingChallengeDto.setMediaUrl("https://example2.com/image.jpg");
      assertEquals("https://example2.com/image.jpg", savingChallengeDto.getMediaUrl());
    }

    @Test
    void getAndSetTimeInterval() {
      savingChallengeDto.setTimeInterval(TimeInterval.WEEKLY);
      assertEquals(TimeInterval.WEEKLY, savingChallengeDto.getTimeInterval());
    }

    @Test
    void getAndSetDifficultyLevel() {
      savingChallengeDto.setDifficultyLevel(DifficultyLevel.HARD);
      assertEquals(DifficultyLevel.HARD, savingChallengeDto.getDifficultyLevel());
    }

    @Test
    void getAndSetExpiryDate() {
      LocalDate testDate = LocalDate.now().plusMonths(2);
      savingChallengeDto.setExpiryDate(testDate);
      assertEquals(testDate, savingChallengeDto.getExpiryDate());
    }

    @Test
    void getAndSetCompleted() {
      savingChallengeDto.setCompleted(true);
      assertTrue(savingChallengeDto.isCompleted());
    }
  }

  @Nested
  class EqualsAndHashcode {
    private SavingChallengeDto anotherSavingChallengeDto;

    @BeforeEach
    void setUp() {
      anotherSavingChallengeDto = new SavingChallengeDto();
      anotherSavingChallengeDto.setId(1L);
      anotherSavingChallengeDto.setDescription("Test Saving Challenge");
      anotherSavingChallengeDto.setTargetAmount(1000.0);
      anotherSavingChallengeDto.setUsedAmount(500.0);
      anotherSavingChallengeDto.setMediaUrl("https://example.com/image.jpg");
      anotherSavingChallengeDto.setTimeInterval(TimeInterval.MONTHLY);
      anotherSavingChallengeDto.setDifficultyLevel(DifficultyLevel.MEDIUM);
      anotherSavingChallengeDto.setExpiryDate(LocalDate.now().plusMonths(1));
      anotherSavingChallengeDto.setCompleted(false);
    }

    @Test
    void whenComparingSameData_thenObjectsAreEqual() {
      assertEquals(savingChallengeDto, anotherSavingChallengeDto);
    }

    @Test
    void whenComparingDifferentData_thenObjectsAreNotEqual() {
      anotherSavingChallengeDto.setDescription("Test Saving Challenge 2");
      assertNotEquals(savingChallengeDto, anotherSavingChallengeDto);
    }

    @Test
    void whenComparingHashcodesOfSameData_thenHashcodesAreEqual() {
      assertEquals(savingChallengeDto.hashCode(), anotherSavingChallengeDto.hashCode());
    }

    @Test
    void whenComparingHashcodesOfDifferentData_thenHashcodesAreNotEqual() {
      anotherSavingChallengeDto.setDescription("Test Saving Challenge 2");
      assertNotEquals(savingChallengeDto.hashCode(), anotherSavingChallengeDto.hashCode());
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(longs = {2L, 3L})
    void differentIdReturnsFalse(Long id) {
      anotherSavingChallengeDto.setId(id);
      assertNotEquals(savingChallengeDto, anotherSavingChallengeDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"Test Saving Challenge 2", "Test Saving Challenge 3"})
    void differentDescriptionReturnsFalse(String description) {
      anotherSavingChallengeDto.setDescription(description);
      assertNotEquals(savingChallengeDto, anotherSavingChallengeDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(doubles = {2000.0, 3000.0})
    void differentTargetAmountReturnsFalse(Double targetAmount) {
      anotherSavingChallengeDto.setTargetAmount(targetAmount);
      assertNotEquals(savingChallengeDto, anotherSavingChallengeDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(doubles = {1000.0, 1500.0})
    void differentUsedAmountReturnsFalse(Double usedAmount) {
      anotherSavingChallengeDto.setUsedAmount(usedAmount);
      assertNotEquals(savingChallengeDto, anotherSavingChallengeDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"https://example2.com/image.jpg", "https://example3.com/image.jpg"})
    void differentMediaUrlReturnsFalse(String mediaUrl) {
      anotherSavingChallengeDto.setMediaUrl(mediaUrl);
      assertNotEquals(savingChallengeDto, anotherSavingChallengeDto);
    }

    @ParameterizedTest
    @EnumSource(
        value = TimeInterval.class,
        mode = EnumSource.Mode.EXCLUDE,
        names = {"MONTHLY"})
    void differentTimeIntervalReturnsFalse(TimeInterval timeInterval) {
      anotherSavingChallengeDto.setTimeInterval(timeInterval);
      assertNotEquals(savingChallengeDto, anotherSavingChallengeDto);
    }

    @ParameterizedTest
    @EnumSource(
        value = DifficultyLevel.class,
        mode = EnumSource.Mode.EXCLUDE,
        names = {"MEDIUM"})
    void differentDifficultyLevelReturnsFalse(DifficultyLevel difficultyLevel) {
      anotherSavingChallengeDto.setDifficultyLevel(difficultyLevel);
      assertNotEquals(savingChallengeDto, anotherSavingChallengeDto);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"2022-01-02", "2023-01-01"})
    void differentExpiryDateReturnsFalse(String dateString) {
      LocalDate date = dateString == null ? null : LocalDate.parse(dateString);
      anotherSavingChallengeDto.setExpiryDate(date);
      assertNotEquals(savingChallengeDto, anotherSavingChallengeDto);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true})
    void differentCompletedReturnsFalse(Boolean completed) {
      anotherSavingChallengeDto.setCompleted(completed);
      assertNotEquals(savingChallengeDto, anotherSavingChallengeDto);
    }
  }

  @Test
  void toStringTest() {
    assertNotNull(savingChallengeDto.toString());
  }
}
