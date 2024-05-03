package edu.ntnu.idi.stud.team10.sparesti.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import edu.ntnu.idi.stud.team10.sparesti.dto.*;
import edu.ntnu.idi.stud.team10.sparesti.enums.DifficultyLevel;
import edu.ntnu.idi.stud.team10.sparesti.enums.OccupationStatus;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;

public class ChallengeGenerator {

  public ChallengeDto randomChallengeGenerator(UserInfoDto userInfo) {
    Random random = new Random();

    if (userInfo.getBudgetingProducts().isEmpty()) {
      return generateRandomSavingChallenge(userInfo, random);
    } else {
      boolean isSavingChallenge = random.nextBoolean();
      if (isSavingChallenge) {
        return generateRandomSavingChallenge(userInfo, random);
      } else {
        return generateRandomPurchaseChallenge(userInfo, random);
      }
    }
  }

  private SavingChallengeDto generateRandomSavingChallenge(UserInfoDto userInfo, Random random) {
    // Get the occupation status of the user
    OccupationStatus occupationStatus = userInfo.getOccupationStatus();
    ChallengeTemplates templates = new ChallengeTemplates();

    // Get the list of challenge templates for the occupation status
    List<String[]> challengeTemplates = templates.getChallengeMap().get(occupationStatus);

    // Select a random challenge template
    String[] challengeTemplate = challengeTemplates.get(random.nextInt(challengeTemplates.size()));

    // Create the saving challenge
    SavingChallengeDto savingChallenge = new SavingChallengeDto();
    savingChallenge.setTitle(challengeTemplate[0]);
    savingChallenge.setDescription(challengeTemplate[1]);
    savingChallenge.setDifficultyLevel(
        DifficultyLevel.values()[random.nextInt(DifficultyLevel.values().length)]);
    savingChallenge.setTimeInterval(
        TimeInterval.values()[random.nextInt(TimeInterval.values().length)]);
    savingChallenge.setTargetAmount(Math.floor(random.nextDouble() * 1000)); // Random amount between 0 and 1000
    savingChallenge.setMediaUrl("🏦");

    return savingChallenge;
  }

  private PurchaseChallengeDto generateRandomPurchaseChallenge(
      UserInfoDto userInfo, Random random) {
    // Randomly select a budgeting product for the purchase challenge
    Set<BudgetingProductDto> budgetingProducts = userInfo.getBudgetingProducts();
    List<BudgetingProductDto> productList = new ArrayList<>(budgetingProducts);
    BudgetingProductDto product = productList.get(random.nextInt(productList.size()));

    // Create the purchase challenge
    PurchaseChallengeDto purchaseChallenge = new PurchaseChallengeDto();
    purchaseChallenge.setTitle(product.getName() + " utfordring");
    purchaseChallenge.setDescription("Kjøp mindre " + product.getName() + " for å spare penger.");
    purchaseChallenge.setDifficultyLevel(
        DifficultyLevel.values()[random.nextInt(DifficultyLevel.values().length)]);
    purchaseChallenge.setTimeInterval(
        TimeInterval.values()[random.nextInt(TimeInterval.values().length)]);
    purchaseChallenge.setTargetAmount(
        (double) (product.getAmount() * getMultiplier(product.getFrequency())));
    purchaseChallenge.setMediaUrl("🛒");
    purchaseChallenge.setProductName(product.getName());

    return purchaseChallenge;
  }

  private static int getMultiplier(TimeInterval frequency) {
    return switch (frequency) {
      case DAILY -> 7;
      case WEEKLY, MONTHLY -> 1;
    };
  }
}
