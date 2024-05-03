package edu.ntnu.idi.stud.team10.sparesti.dto;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;

import static org.junit.jupiter.api.Assertions.*;

public class ChallengeDtoTest {
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
