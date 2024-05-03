package edu.ntnu.idi.stud.team10.sparesti.util;

import edu.ntnu.idi.stud.team10.sparesti.dto.ChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.PurchaseChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.SavingChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;

/** A class for parsing challenges from text responses. */
public class ChallengeParser {

  /**
   * Parses a challenge from a text response.
   *
   * @param textResponse The text response.
   * @return The parsed challenge.
   */
  public static ChallengeDto parse(String textResponse) {
    if (textResponse.toLowerCase().contains("saving")) {
      return parseSavingChallenge(textResponse);
    } else if (textResponse.toLowerCase().contains("purchase")) {
      return parsePurchaseChallenge(textResponse);
    } else {
      throw new IllegalArgumentException("Invalid challenge format: missing challenge type field.");
    }
  }

  /**
   * Parses a saving challenge from a text response.
   *
   * @param textResponse The text response.
   * @return The parsed saving challenge.
   */
  private static SavingChallengeDto parseSavingChallenge(String textResponse) {
    SavingChallengeDto savingChallengeDTO = new SavingChallengeDto();
    String[] lines = textResponse.split("\n");

    // Parse fields from text response
    boolean titleFound = false;
    boolean descriptionFound = false;
    boolean difficultyLevelFound = false;
    boolean timeIntervalFound = false;
    boolean targetAmountFound = false;
    boolean mediaUrlFound = false;

    for (String line : lines) {
      if (line.toLowerCase().startsWith("title:")) {
        savingChallengeDTO.setTitle(line.substring("title: ".length()).trim());
        titleFound = true;
      } else if (line.toLowerCase().startsWith("description:")) {
        savingChallengeDTO.setDescription(line.substring("description: ".length()).trim());
        descriptionFound = true;
      } else if (line.toLowerCase().startsWith("difficulty level:")) {
        String difficultyLevel = line.toLowerCase().substring("difficulty level: ".length()).trim();
        savingChallengeDTO.setDifficultyLevel(
            DifficultyLevel.valueOf(difficultyLevel.toUpperCase()));
        difficultyLevelFound = true;
      } else if (line.toLowerCase().startsWith("time interval:")) {
        String timeInterval = line.toLowerCase().substring("time interval: ".length()).trim();
        savingChallengeDTO.setTimeInterval(TimeInterval.valueOf(timeInterval.toUpperCase()));
        timeIntervalFound = true;
      } else if (line.toLowerCase().startsWith("target amount:")) {
        savingChallengeDTO.setTargetAmount(
            Double.parseDouble(line.substring("target amount: ".length()).trim()));
        targetAmountFound = true;
      } else if (line.toLowerCase().startsWith("media url:")) {
        savingChallengeDTO.setMediaUrl(line.substring("media url: ".length()).trim());
        mediaUrlFound = true;
      }
    }

    // Check that all required fields have been set
    if (!titleFound
        || !descriptionFound
        || !difficultyLevelFound
        || !timeIntervalFound
        || !targetAmountFound
        || !mediaUrlFound) {
      throw new IllegalArgumentException(
          "Invalid challenge format: missing required fields, got "
              + titleFound
              + descriptionFound
              + difficultyLevelFound
              + timeIntervalFound
              + targetAmountFound
              + mediaUrlFound
              + " fields.");
    }

    return savingChallengeDTO;
  }

  /**
   * Parses a purchase challenge from a text response.
   *
   * @param textResponse The text response.
   * @return The parsed purchase challenge.
   */
  private static PurchaseChallengeDto parsePurchaseChallenge(String textResponse) {
    PurchaseChallengeDto purchaseChallengeDTO = new PurchaseChallengeDto();
    String[] lines = textResponse.split("\n");

    boolean titleFound = false;
    boolean descriptionFound = false;
    boolean difficultyLevelFound = false;
    boolean timeIntervalFound = false;
    boolean targetAmountFound = false;
    boolean mediaUrlFound = false;
    boolean productNameFound = false;

    // Parse fields from text response
    for (String line : lines) {
      if (line.toLowerCase().startsWith("title:")) {
        purchaseChallengeDTO.setTitle(line.substring("title: ".length()).trim());
        titleFound = true;
      } else if (line.toLowerCase().startsWith("description:")) {
        purchaseChallengeDTO.setDescription(line.substring("description: ".length()).trim());
        descriptionFound = true;
      } else if (line.toLowerCase().startsWith("difficulty level:")) {
        String difficultyLevel = line.toLowerCase().substring("difficulty level: ".length()).trim();
        purchaseChallengeDTO.setDifficultyLevel(
            DifficultyLevel.valueOf(difficultyLevel.toUpperCase()));
        difficultyLevelFound = true;
      } else if (line.toLowerCase().startsWith("time interval:")) {
        String timeInterval = line.toLowerCase().substring("time interval: ".length()).trim();
        purchaseChallengeDTO.setTimeInterval(TimeInterval.valueOf(timeInterval.toUpperCase()));
        timeIntervalFound = true;
      } else if (line.toLowerCase().startsWith("target amount:")) {
        purchaseChallengeDTO.setTargetAmount(
            Double.parseDouble(line.substring("target amount: ".length()).trim()));
        targetAmountFound = true;
      } else if (line.toLowerCase().startsWith("media url:")) {
        purchaseChallengeDTO.setMediaUrl(line.substring("media url: ".length()).trim());
        mediaUrlFound = true;
      } else if (line.toLowerCase().startsWith("product name:")) {
        purchaseChallengeDTO.setProductName(line.substring("product name: ".length()).trim());
        productNameFound = true;
      }
    }

    // Check that all required fields have been set
    if (!titleFound
        || !descriptionFound
        || !difficultyLevelFound
        || !timeIntervalFound
        || !targetAmountFound
        || !mediaUrlFound
        || !productNameFound) {
      throw new IllegalArgumentException(
          "Invalid challenge format: missing required fields, got "
              + titleFound
              + descriptionFound
              + difficultyLevelFound
              + timeIntervalFound
              + targetAmountFound
              + mediaUrlFound
              + productNameFound
              + " fields.");
    }

    return purchaseChallengeDTO;
  }
}
