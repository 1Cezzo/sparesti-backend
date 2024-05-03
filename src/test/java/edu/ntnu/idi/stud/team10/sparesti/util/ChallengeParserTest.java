package edu.ntnu.idi.stud.team10.sparesti.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.dto.ChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.PurchaseChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.SavingChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;

public class ChallengeParserTest {

  @Test
  public void testParseSavingChallenge() {
    String textResponse =
        "Title: Saving Challenge\n"
            + "Description: Save money for a vacation\n"
            + "Difficulty Level: EASY\n"
            + "Time Interval: MONTHLY\n"
            + "Target Amount: 1000.0\n"
            + "Media URL: https://example.com/image.jpg";

    SavingChallengeDto savingChallengeDto = ChallengeParser.parseSavingChallenge(textResponse);

    Assertions.assertEquals("Saving Challenge", savingChallengeDto.getTitle());
    Assertions.assertEquals("Save money for a vacation", savingChallengeDto.getDescription());
    Assertions.assertEquals(DifficultyLevel.EASY, savingChallengeDto.getDifficultyLevel());
    Assertions.assertEquals(TimeInterval.MONTHLY, savingChallengeDto.getTimeInterval());
    Assertions.assertEquals(1000.0, savingChallengeDto.getTargetAmount());
    Assertions.assertEquals("https://example.com/image.jpg", savingChallengeDto.getMediaUrl());
  }

  @Test
  public void testParsePurchaseChallenge() {
    String textResponse =
        "Title: Purchase Challenge\n"
            + "Description: Buy a new laptop\n"
            + "Difficulty Level: HARD\n"
            + "Time Interval: WEEKLY\n"
            + "Target Amount: 2000.0\n"
            + "Media URL: https://example.com/image.jpg\n"
            + "Product Name: Laptop";

    PurchaseChallengeDto purchaseChallengeDto =
        ChallengeParser.parsePurchaseChallenge(textResponse);

    Assertions.assertEquals("Purchase Challenge", purchaseChallengeDto.getTitle());
    Assertions.assertEquals("Buy a new laptop", purchaseChallengeDto.getDescription());
    Assertions.assertEquals(DifficultyLevel.HARD, purchaseChallengeDto.getDifficultyLevel());
    Assertions.assertEquals(TimeInterval.WEEKLY, purchaseChallengeDto.getTimeInterval());
    Assertions.assertEquals(2000.0, purchaseChallengeDto.getTargetAmount());
    Assertions.assertEquals("https://example.com/image.jpg", purchaseChallengeDto.getMediaUrl());
    Assertions.assertEquals("Laptop", purchaseChallengeDto.getProductName());
  }

  @Test
  public void testParseSaving() {
    String textResponse =
        "Title: Saving Challenge\n"
            + "Description: Save money for a vacation\n"
            + "Difficulty Level: EASY\n"
            + "Time Interval: MONTHLY\n"
            + "Target Amount: 1000.0\n"
            + "Media URL: https://example.com/image.jpg";

    ChallengeDto challengeParsed = ChallengeParser.parse(textResponse);

    Assertions.assertEquals("Saving Challenge", challengeParsed.getTitle());
    Assertions.assertEquals("Save money for a vacation", challengeParsed.getDescription());
    Assertions.assertEquals(DifficultyLevel.EASY, challengeParsed.getDifficultyLevel());
    Assertions.assertEquals(TimeInterval.MONTHLY, challengeParsed.getTimeInterval());
    Assertions.assertEquals(1000.0, challengeParsed.getTargetAmount());
    Assertions.assertEquals("https://example.com/image.jpg", challengeParsed.getMediaUrl());
  }

  @Test
  public void testParsePurchase() {
    String textResponse =
        "Title: Purchase Challenge\n"
            + "Description: Buy a new laptop\n"
            + "Difficulty Level: HARD\n"
            + "Time Interval: WEEKLY\n"
            + "Target Amount: 2000.0\n"
            + "Media URL: https://example.com/image.jpg\n"
            + "Product Name: Laptop";

    ChallengeDto challengeParsed = ChallengeParser.parse(textResponse);

    Assertions.assertEquals("Purchase Challenge", challengeParsed.getTitle());
    Assertions.assertEquals("Buy a new laptop", challengeParsed.getDescription());
    Assertions.assertEquals(DifficultyLevel.HARD, challengeParsed.getDifficultyLevel());
    Assertions.assertEquals(TimeInterval.WEEKLY, challengeParsed.getTimeInterval());
    Assertions.assertEquals(2000.0, challengeParsed.getTargetAmount());
    Assertions.assertEquals("https://example.com/image.jpg", challengeParsed.getMediaUrl());
    Assertions.assertEquals("Laptop", ((PurchaseChallengeDto) challengeParsed).getProductName());
  }

  @Test
  public void testConstructor() {
    ChallengeParser challengeParser = new ChallengeParser();

    Assertions.assertNotNull(challengeParser);
  }
}
