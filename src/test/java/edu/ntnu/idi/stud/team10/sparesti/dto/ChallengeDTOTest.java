package edu.ntnu.idi.stud.team10.sparesti.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;
import edu.ntnu.idi.stud.team10.sparesti.model.Challenge;

public class ChallengeDTOTest {

    private ChallengeDTO challengeDto;

    @BeforeEach
    public void setUp() {
        challengeDto = new ChallengeDTO();
        challengeDto.setDescription("Test Challenge");
        challengeDto.setTargetAmount(1000.0);
        challengeDto.setSavedAmount(500.0);
        challengeDto.setMediaUrl("https://example.com/image.jpg");
        challengeDto.setTimeInterval(TimeInterval.MONTHLY);
        challengeDto.setDifficultyLevel(DifficultyLevel.MEDIUM);
        challengeDto.setExpiryDate(LocalDate.now().plusMonths(1));
        challengeDto.setCompleted(false);
    }

    @Test
    public void testChallengeDtoFields() {
        assertEquals("Test Challenge", challengeDto.getDescription());
        assertEquals(1000.0, challengeDto.getTargetAmount());
        assertEquals(500.0, challengeDto.getSavedAmount());
        assertEquals("https://example.com/image.jpg", challengeDto.getMediaUrl());
        assertEquals(TimeInterval.MONTHLY, challengeDto.getTimeInterval());
        assertEquals(DifficultyLevel.MEDIUM, challengeDto.getDifficultyLevel());
        assertEquals(LocalDate.now().plusMonths(1), challengeDto.getExpiryDate());
        assertEquals(false, challengeDto.isCompleted());
    }

    @Test
    public void testChallengeDtoConversionToEntity() {
        Challenge challenge = challengeDto.toEntity();

        assertEquals("Test Challenge", challenge.getDescription());
        assertEquals(1000.0, challenge.getTargetAmount());
        assertEquals(500.0, challenge.getSavedAmount());
        assertEquals("https://example.com/image.jpg", challenge.getMediaUrl());
        assertEquals(TimeInterval.MONTHLY, challenge.getTimeInterval());
        assertEquals(DifficultyLevel.MEDIUM, challenge.getDifficultyLevel());
        assertEquals(LocalDate.now().plusMonths(1), challenge.getExpiryDate());
        assertEquals(false, challenge.isCompleted());
    }
}
