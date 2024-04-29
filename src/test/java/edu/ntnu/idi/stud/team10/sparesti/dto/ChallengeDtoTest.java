package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;
import edu.ntnu.idi.stud.team10.sparesti.model.Challenge;

import static org.junit.jupiter.api.Assertions.*;

public class ChallengeDtoTest {

  @Test
  public void shouldConvertToEntityWithSameValues() {
    ChallengeDto challengeDto = new ChallengeDto();
    challengeDto.setDescription("Test Challenge");
    challengeDto.setTargetAmount(1000.0);
    challengeDto.setUsedAmount(500.0);
    challengeDto.setMediaUrl("https://example.com/image.jpg");
    challengeDto.setTimeInterval(TimeInterval.MONTHLY);
    challengeDto.setDifficultyLevel(DifficultyLevel.MEDIUM);
    challengeDto.setExpiryDate(LocalDate.now().plusMonths(1));
    challengeDto.setCompleted(false);

    Challenge challenge = challengeDto.toEntity();

    assertEquals(challengeDto.getDescription(), challenge.getDescription());
    assertEquals(challengeDto.getTargetAmount(), challenge.getTargetAmount());
    assertEquals(challengeDto.getUsedAmount(), challenge.getUsedAmount());
    assertEquals(challengeDto.getMediaUrl(), challenge.getMediaUrl());
    assertEquals(challengeDto.getTimeInterval(), challenge.getTimeInterval());
    assertEquals(challengeDto.getDifficultyLevel(), challenge.getDifficultyLevel());
    assertEquals(challengeDto.getExpiryDate(), challenge.getExpiryDate());
    assertEquals(challengeDto.isCompleted(), challenge.isCompleted());
  }

  @Test
  public void shouldCreateEntityWithNullValuesWhenDtoFieldsAreNull() {
    ChallengeDto challengeDto = new ChallengeDto();
    challengeDto.setDescription(null);
    challengeDto.setTargetAmount(0.0);
    challengeDto.setUsedAmount(0.0);
    challengeDto.setMediaUrl(null);
    challengeDto.setTimeInterval(null);
    challengeDto.setDifficultyLevel(null);
    challengeDto.setExpiryDate(null);
    challengeDto.setCompleted(false);

    Challenge challenge = challengeDto.toEntity();

    assertNull(challenge.getDescription());
    assertEquals(0.0, challenge.getTargetAmount());
    assertEquals(0.0, challenge.getUsedAmount());
    assertNull(challenge.getMediaUrl());
    assertNull(challenge.getTimeInterval());
    assertNull(challenge.getDifficultyLevel());
    assertNull(challenge.getExpiryDate());
    assertFalse(challenge.isCompleted());
  }

  @Test
  public void shouldReturnTrueWhenComparingSameInstance() {
    ChallengeDto challengeDto = new ChallengeDto();
    assertTrue(challengeDto.equals(challengeDto));
  }

  @Test
  public void shouldReturnFalseWhenComparingWithNull() {
    ChallengeDto challengeDto = new ChallengeDto();
    assertFalse(challengeDto.equals(null));
  }

  @Test
  public void shouldReturnFalseWhenComparingWithDifferentType() {
    ChallengeDto challengeDto = new ChallengeDto();
    assertFalse(challengeDto.equals("test"));
  }

  @Test
  public void shouldReturnTrueWhenComparingEqualObjects() {
    ChallengeDto challengeDto1 = new ChallengeDto();
    challengeDto1.setDescription("Test Challenge");
    challengeDto1.setTargetAmount(1000.0);
    challengeDto1.setUsedAmount(500.0);
    challengeDto1.setMediaUrl("https://example.com/image.jpg");
    challengeDto1.setTimeInterval(TimeInterval.MONTHLY);
    challengeDto1.setDifficultyLevel(DifficultyLevel.MEDIUM);
    challengeDto1.setExpiryDate(LocalDate.now().plusMonths(1));
    challengeDto1.setCompleted(false);

    ChallengeDto challengeDto2 = new ChallengeDto();
    challengeDto2.setDescription("Test Challenge");
    challengeDto2.setTargetAmount(1000.0);
    challengeDto2.setUsedAmount(500.0);
    challengeDto2.setMediaUrl("https://example.com/image.jpg");
    challengeDto2.setTimeInterval(TimeInterval.MONTHLY);
    challengeDto2.setDifficultyLevel(DifficultyLevel.MEDIUM);
    challengeDto2.setExpiryDate(LocalDate.now().plusMonths(1));
    challengeDto2.setCompleted(false);

    assertTrue(challengeDto1.equals(challengeDto2));
    assertTrue(challengeDto2.equals(challengeDto1));
  }

  @Test
  public void shouldReturnSameHashCodeForEqualObjects() {
    ChallengeDto challengeDto1 = new ChallengeDto();
    challengeDto1.setDescription("Test Challenge");
    challengeDto1.setTargetAmount(1000.0);
    challengeDto1.setUsedAmount(500.0);
    challengeDto1.setMediaUrl("https://example.com/image.jpg");
    challengeDto1.setTimeInterval(TimeInterval.MONTHLY);
    challengeDto1.setDifficultyLevel(DifficultyLevel.MEDIUM);
    challengeDto1.setExpiryDate(LocalDate.now().plusMonths(1));
    challengeDto1.setCompleted(false);

    ChallengeDto challengeDto2 = new ChallengeDto();
    challengeDto2.setDescription("Test Challenge");
    challengeDto2.setTargetAmount(1000.0);
    challengeDto2.setUsedAmount(500.0);
    challengeDto2.setMediaUrl("https://example.com/image.jpg");
    challengeDto2.setTimeInterval(TimeInterval.MONTHLY);
    challengeDto2.setDifficultyLevel(DifficultyLevel.MEDIUM);
    challengeDto2.setExpiryDate(LocalDate.now().plusMonths(1));
    challengeDto2.setCompleted(false);

    assertEquals(challengeDto1.hashCode(), challengeDto2.hashCode());
  }
}
