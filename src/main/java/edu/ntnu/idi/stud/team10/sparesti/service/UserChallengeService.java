package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.ntnu.idi.stud.team10.sparesti.dto.*;
import edu.ntnu.idi.stud.team10.sparesti.model.*;
import edu.ntnu.idi.stud.team10.sparesti.repository.ChallengeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;

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
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

    T challengeToRemove =
        challengeRepository
            .findById(challengeId)
            .orElseThrow(() -> new NotFoundException("Challenge not found"));

    user.removeChallenge(challengeToRemove);
    userRepository.save(user);

    return new UserDto(user);
  }

  /**
   * Get a sorted list of challenges for the user.
   *
   * @param userId the id of the user.
   * @return a list of sorted challenges.
   */
  @Transactional(readOnly = true)
  public List<ChallengeDTO> getSortedChallengesByUser(Long userId) {
    List<ChallengeDTO> allChallenges = new ArrayList<>();

    // Fetch challenges for the user
    List<ConsumptionChallengeDTO> consumptionChallenges = fetchConsumptionChallengesForUser(userId);
    List<PurchaseChallengeDTO> purchaseChallenges = fetchPurchaseChallengesForUser(userId);
    List<SavingChallengeDTO> savingChallenges = fetchSavingChallengesForUser(userId);

    // Add all challenges to the list
    allChallenges.addAll(consumptionChallenges);
    allChallenges.addAll(purchaseChallenges);
    allChallenges.addAll(savingChallenges);

    // Sort the challenges
    allChallenges.sort(
        (a, b) -> {
          // First, sort by completion status
          if (a.isCompleted() && !b.isCompleted()) {
            return -1; // a comes before b if a is completed and b is not
          }
          if (!a.isCompleted() && b.isCompleted()) {
            return 1; // b comes before a if b is completed and a is not
          }

          // If completion status is the same, sort by expiry date
          int dateComparison = b.getExpiryDate().compareTo(a.getExpiryDate());
          if (dateComparison != 0) {
            return dateComparison; // If expiry dates are different, return the comparison result
          }

          // If expiry dates are the same, sort by challenge ID
          return Long.compare(a.getId(), b.getId());
        });

    return allChallenges;
  }

  /**
   * Get consumption challenges for a user.
   *
   * @param userId the id of the user.
   * @return a list of consumption challenges for the user.
   */
  public List<ConsumptionChallengeDTO> fetchConsumptionChallengesForUser(Long userId) {
    User user =
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

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
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

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
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

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
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

    T challenge =
        challengeRepository
            .findById(challengeId)
            .orElseThrow(() -> new NotFoundException("Challenge not found"));

    user.addChallenge(challenge);
    userRepository.save(user);

    return new UserDto(user);
  }
}
