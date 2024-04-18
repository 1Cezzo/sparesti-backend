package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ntnu.idi.stud.team10.sparesti.dto.ConsumptionChallengeDTO;
import edu.ntnu.idi.stud.team10.sparesti.dto.PurchaseChallengeDTO;
import edu.ntnu.idi.stud.team10.sparesti.dto.SavingChallengeDTO;
import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;
import edu.ntnu.idi.stud.team10.sparesti.model.ConsumptionChallenge;
import edu.ntnu.idi.stud.team10.sparesti.model.PurchaseChallenge;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingChallenge;
import edu.ntnu.idi.stud.team10.sparesti.repository.ChallengeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.ConsumptionChallengeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.PurchaseChallengeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.SavingChallengeRepository;

@Service
public class ChallengeService {

  private final ChallengeRepository challengeRepository;
  private final ConsumptionChallengeRepository consumptionChallengeRepository;
  private final PurchaseChallengeRepository purchaseChallengeRepository;
  private final SavingChallengeRepository savingChallengeRepository;

  @Autowired
  public ChallengeService(
      ChallengeRepository challengeRepository,
      ConsumptionChallengeRepository consumptionChallengeRepository,
      PurchaseChallengeRepository purchaseChallengeRepository,
      SavingChallengeRepository savingChallengeRepository) {
    this.challengeRepository = challengeRepository;
    this.consumptionChallengeRepository = consumptionChallengeRepository;
    this.purchaseChallengeRepository = purchaseChallengeRepository;
    this.savingChallengeRepository = savingChallengeRepository;
  }

  public ConsumptionChallengeDTO createConsumptionChallenge(ConsumptionChallengeDTO dto) {
    ConsumptionChallenge challenge = dto.toEntity();
    challenge.setSavedAmount(0.0);
    LocalDate expiryDate = calculateExpiryDate(dto.getTimeInterval());
    challenge.setExpiryDate(expiryDate);
    challenge.setCompleted(false);
    ConsumptionChallenge savedChallenge = consumptionChallengeRepository.save(challenge);
    return new ConsumptionChallengeDTO(savedChallenge);
  }

  public PurchaseChallengeDTO createPurchaseChallenge(PurchaseChallengeDTO dto) {
    PurchaseChallenge challenge = dto.toEntity();
    challenge.setSavedAmount(0.0);
    LocalDate expiryDate = calculateExpiryDate(dto.getTimeInterval());
    challenge.setExpiryDate(expiryDate);
    challenge.setCompleted(false);
    PurchaseChallenge savedChallenge = purchaseChallengeRepository.save(challenge);
    return new PurchaseChallengeDTO(savedChallenge);
  }

  public SavingChallengeDTO createSavingChallenge(SavingChallengeDTO dto) {
    SavingChallenge challenge = dto.toEntity();
    challenge.setSavedAmount(0.0);
    LocalDate expiryDate = calculateExpiryDate(dto.getTimeInterval());
    challenge.setExpiryDate(expiryDate);
    SavingChallenge savedChallenge = savingChallengeRepository.save(challenge);
    return new SavingChallengeDTO(savedChallenge);
  }

  private LocalDate calculateExpiryDate(TimeInterval timeInterval) {
    LocalDate currentDate = LocalDate.now();
    return switch (timeInterval) {
      case DAILY -> currentDate.plusDays(1);
      case WEEKLY -> currentDate.plusWeeks(1);
      case MONTHLY -> currentDate.plusMonths(1);
      default -> currentDate;
    };
  }
}
