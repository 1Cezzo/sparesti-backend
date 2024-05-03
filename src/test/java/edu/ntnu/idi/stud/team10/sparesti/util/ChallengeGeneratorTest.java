package edu.ntnu.idi.stud.team10.sparesti.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetingProductDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.PurchaseChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.SavingChallengeDto;
import edu.ntnu.idi.stud.team10.sparesti.dto.UserInfoDto;
import edu.ntnu.idi.stud.team10.sparesti.enums.OccupationStatus;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;

public class ChallengeGeneratorTest {

  private ChallengeTemplates challengeTemplates;

  @BeforeEach
  public void setUp() {
    challengeTemplates = new ChallengeTemplates();
  }

  @Test
  public void testGenerateRandomSavingChallenge() {
    ChallengeGenerator challengeGenerator = new ChallengeGenerator(challengeTemplates);
    UserInfoDto userInfo = new UserInfoDto();
    userInfo.setOccupationStatus(OccupationStatus.STUDENT);
    SavingChallengeDto savingChallenge =
        challengeGenerator.generateRandomSavingChallenge(userInfo, new Random());
    Assertions.assertNotNull(savingChallenge);
    Assertions.assertNotNull(savingChallenge.getTitle());
    Assertions.assertNotNull(savingChallenge.getDescription());
    Assertions.assertNotNull(savingChallenge.getDifficultyLevel());
    Assertions.assertNotNull(savingChallenge.getTimeInterval());
    Assertions.assertNotNull(savingChallenge.getTargetAmount());
    Assertions.assertNotNull(savingChallenge.getMediaUrl());
  }

  @Test
  public void testGenerateRandomPurchaseChallenge() {
    ChallengeGenerator challengeGenerator = new ChallengeGenerator(challengeTemplates);
    UserInfoDto userInfo = new UserInfoDto();
    Set<BudgetingProductDto> budgetingProducts = new HashSet<>();
    BudgetingProductDto product = new BudgetingProductDto();
    product.setName("Product");
    product.setAmount(10);
    product.setFrequency(TimeInterval.WEEKLY);
    budgetingProducts.add(product);
    userInfo.setBudgetingProducts(budgetingProducts);
    PurchaseChallengeDto purchaseChallenge =
        challengeGenerator.generateRandomPurchaseChallenge(userInfo, new Random());
    Assertions.assertNotNull(purchaseChallenge);
    Assertions.assertNotNull(purchaseChallenge.getTitle());
    Assertions.assertNotNull(purchaseChallenge.getDescription());
    Assertions.assertNotNull(purchaseChallenge.getDifficultyLevel());
    Assertions.assertNotNull(purchaseChallenge.getTimeInterval());
    Assertions.assertNotNull(purchaseChallenge.getTargetAmount());
    Assertions.assertNotNull(purchaseChallenge.getMediaUrl());
    Assertions.assertNotNull(purchaseChallenge.getProductName());
  }

  @Test
  public void testGetMultiplier() {
    ChallengeGenerator challengeGenerator = new ChallengeGenerator(challengeTemplates);
    int multiplier = challengeGenerator.getMultiplier(TimeInterval.DAILY);
    Assertions.assertEquals(7, multiplier);

    multiplier = challengeGenerator.getMultiplier(TimeInterval.WEEKLY);
    Assertions.assertEquals(1, multiplier);

    multiplier = challengeGenerator.getMultiplier(TimeInterval.MONTHLY);
    Assertions.assertEquals(1, multiplier);
  }
}
