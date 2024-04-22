package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ntnu.idi.stud.team10.sparesti.dto.*;
import edu.ntnu.idi.stud.team10.sparesti.model.*;
import edu.ntnu.idi.stud.team10.sparesti.repository.ChallengeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;

/**
 * Service for User Challenge entities.
 *
 * @param <T> the type of the challenge.
 */
@Service
public class UserChallengeService<T extends Challenge> {

  private final ChallengeRepository<T> challengeRepository;
  private final UserRepository userRepository;

  public UserChallengeService(
      ChallengeRepository<T> challengeRepository, UserRepository userRepository) {
    this.challengeRepository = challengeRepository;
    this.userRepository = userRepository;
  }

  /**
   * Remove a challenge from a user.
   *
   * @param userId the id of the user.
   * @param challengeId the id of the challenge.
   * @return the updated user.
   */
  public UserDto removeChallengeFromUser(Long userId, Long challengeId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

    T challengeToRemove =
        challengeRepository
            .findById(challengeId)
            .orElseThrow(() -> new IllegalArgumentException("Challenge not found"));

    user.removeChallenge(challengeToRemove);
    userRepository.save(user);

    return new UserDto(user);
  }

  /**
   * Get all challenges for a user.
   *
   * @param userId the id of the user.
   * @return a map of challenges for the user.
   */
  @Transactional(readOnly = true)
  public Map<String, List<? extends ChallengeDTO>> getChallengesByUser(Long userId) {
    Map<String, List<? extends ChallengeDTO>> challengesMap = new HashMap<>();

    List<ConsumptionChallengeDTO> consumptionChallenges = fetchConsumptionChallengesForUser(userId);
    List<PurchaseChallengeDTO> purchaseChallenges = fetchPurchaseChallengesForUser(userId);
    List<SavingChallengeDTO> savingChallenges = fetchSavingChallengesForUser(userId);

    challengesMap.put("consumptionChallenges", consumptionChallenges);
    challengesMap.put("purchaseChallenges", purchaseChallenges);
    challengesMap.put("savingChallenges", savingChallenges);

    return challengesMap;
  }

  /**
   * Get consumption challenges for a user.
   *
   * @param userId the id of the user.
   * @return a list of consumption challenges for the user.
   */
  public List<ConsumptionChallengeDTO> fetchConsumptionChallengesForUser(Long userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

    return user.getChallenges().stream()
        .filter(challenge -> challenge instanceof ConsumptionChallenge)
        .map(challenge -> new ConsumptionChallengeDTO((ConsumptionChallenge) challenge))
        .collect(Collectors.toList());
  }

  /**
   * Get purchase challenges for a user.
   *
   * @param userId the id of the user.
   * @return a list of purchase challenges for the user.
   */
  public List<PurchaseChallengeDTO> fetchPurchaseChallengesForUser(Long userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

    return user.getChallenges().stream()
        .filter(challenge -> challenge instanceof PurchaseChallenge)
        .map(challenge -> new PurchaseChallengeDTO((PurchaseChallenge) challenge))
        .collect(Collectors.toList());
  }

  /**
   * Get saving challenges for a user.
   *
   * @param userId the id of the user.
   * @return a list of saving challenges for the user.
   */
  public List<SavingChallengeDTO> fetchSavingChallengesForUser(Long userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

    return user.getChallenges().stream()
        .filter(challenge -> challenge instanceof SavingChallenge)
        .map(challenge -> new SavingChallengeDTO((SavingChallenge) challenge))
        .collect(Collectors.toList());
  }

  /**
   * Add a challenge to a user.
   *
   * @param userId the id of the user.
   * @param challengeId the id of the challenge.
   * @return the updated user.
   */
  public UserDto addChallengeToUser(Long userId, Long challengeId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

    T challenge =
        challengeRepository
            .findById(challengeId)
            .orElseThrow(() -> new IllegalArgumentException("Challenge not found"));

    user.addChallenge(challenge);
    userRepository.save(user);

    return new UserDto(user);
  }
}
