package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;
import edu.ntnu.idi.stud.team10.sparesti.model.Challenge;

import static org.junit.jupiter.api.Assertions.*;

public class ChallengeDTOTest {

  @Test
  public void shouldConvertToEntityWithSameValues() {
    ChallengeDTO challengeDto = new ChallengeDTO();
    challengeDto.setDescription("Test Challenge");
    challengeDto.setTargetAmount(1000.0);
    challengeDto.setSavedAmount(500.0);
    challengeDto.setMediaUrl("https://example.com/image.jpg");
    challengeDto.setTimeInterval(TimeInterval.MONTHLY);
    challengeDto.setDifficultyLevel(DifficultyLevel.MEDIUM);
    challengeDto.setExpiryDate(LocalDate.now().plusMonths(1));
    challengeDto.setCompleted(false);

    Challenge challenge = challengeDto.toEntity();

    assertEquals(challengeDto.getDescription(), challenge.getDescription());
    assertEquals(challengeDto.getTargetAmount(), challenge.getTargetAmount());
    assertEquals(challengeDto.getSavedAmount(), challenge.getSavedAmount());
    assertEquals(challengeDto.getMediaUrl(), challenge.getMediaUrl());
    assertEquals(challengeDto.getTimeInterval(), challenge.getTimeInterval());
    assertEquals(challengeDto.getDifficultyLevel(), challenge.getDifficultyLevel());
    assertEquals(challengeDto.getExpiryDate(), challenge.getExpiryDate());
    assertEquals(challengeDto.isCompleted(), challenge.isCompleted());
  }

  @Test
  public void shouldCreateEntityWithNullValuesWhenDtoFieldsAreNull() {
    ChallengeDTO challengeDto = new ChallengeDTO();
    challengeDto.setDescription(null);
    challengeDto.setTargetAmount(0.0);
    challengeDto.setSavedAmount(0.0);
    challengeDto.setMediaUrl(null);
    challengeDto.setTimeInterval(null);
    challengeDto.setDifficultyLevel(null);
    challengeDto.setExpiryDate(null);
    challengeDto.setCompleted(false);

    Challenge challenge = challengeDto.toEntity();

    assertNull(challenge.getDescription());
    assertEquals(0.0, challenge.getTargetAmount());
    assertEquals(0.0, challenge.getSavedAmount());
    assertNull(challenge.getMediaUrl());
    assertNull(challenge.getTimeInterval());
    assertNull(challenge.getDifficultyLevel());
    assertNull(challenge.getExpiryDate());
    assertFalse(challenge.isCompleted());
  }

  @Test
  public void shouldReturnTrueWhenComparingSameInstance() {
    ChallengeDTO challengeDto = new ChallengeDTO();
    assertTrue(challengeDto.equals(challengeDto));
  }

  @Test
  public void shouldReturnFalseWhenComparingWithNull() {
    ChallengeDTO challengeDto = new ChallengeDTO();
    assertFalse(challengeDto.equals(null));
  }

  @Test
  public void shouldReturnFalseWhenComparingWithDifferentType() {
    ChallengeDTO challengeDto = new ChallengeDTO();
    assertFalse(challengeDto.equals("test"));
  }

  @Test
  public void shouldReturnTrueWhenComparingEqualObjects() {
    ChallengeDTO challengeDto1 = new ChallengeDTO();
    challengeDto1.setDescription("Test Challenge");
    challengeDto1.setTargetAmount(1000.0);
    challengeDto1.setSavedAmount(500.0);
    challengeDto1.setMediaUrl("https://example.com/image.jpg");
    challengeDto1.setTimeInterval(TimeInterval.MONTHLY);
    challengeDto1.setDifficultyLevel(DifficultyLevel.MEDIUM);
    challengeDto1.setExpiryDate(LocalDate.now().plusMonths(1));
    challengeDto1.setCompleted(false);

    ChallengeDTO challengeDto2 = new ChallengeDTO();
    challengeDto2.setDescription("Test Challenge");
    challengeDto2.setTargetAmount(1000.0);
    challengeDto2.setSavedAmount(500.0);
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
    ChallengeDTO challengeDto1 = new ChallengeDTO();
    challengeDto1.setDescription("Test Challenge");
    challengeDto1.setTargetAmount(1000.0);
    challengeDto1.setSavedAmount(500.0);
    challengeDto1.setMediaUrl("https://example.com/image.jpg");
    challengeDto1.setTimeInterval(TimeInterval.MONTHLY);
    challengeDto1.setDifficultyLevel(DifficultyLevel.MEDIUM);
    challengeDto1.setExpiryDate(LocalDate.now().plusMonths(1));
    challengeDto1.setCompleted(false);

    ChallengeDTO challengeDto2 = new ChallengeDTO();
    challengeDto2.setDescription("Test Challenge");
    challengeDto2.setTargetAmount(1000.0);
    challengeDto2.setSavedAmount(500.0);
    challengeDto2.setMediaUrl("https://example.com/image.jpg");
    challengeDto2.setTimeInterval(TimeInterval.MONTHLY);
    challengeDto2.setDifficultyLevel(DifficultyLevel.MEDIUM);
    challengeDto2.setExpiryDate(LocalDate.now().plusMonths(1));
    challengeDto2.setCompleted(false);

    assertEquals(challengeDto1.hashCode(), challengeDto2.hashCode());
  }
}
