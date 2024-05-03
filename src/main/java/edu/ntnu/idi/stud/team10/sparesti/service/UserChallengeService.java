package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDate;
import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ntnu.idi.stud.team10.sparesti.dto.*;
import edu.ntnu.idi.stud.team10.sparesti.mapper.ChallengeMapper;
import edu.ntnu.idi.stud.team10.sparesti.mapper.SavingsGoalMapper;
import edu.ntnu.idi.stud.team10.sparesti.mapper.UserMapper;
import edu.ntnu.idi.stud.team10.sparesti.model.*;
import edu.ntnu.idi.stud.team10.sparesti.repository.ChallengeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.ChallengeGenerator;
import edu.ntnu.idi.stud.team10.sparesti.util.ChallengeParser;
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
  private final ChatGPTService chatGPTService;
  private final UserMapper userMapper;
  private final ChallengeMapper challengeMapper;
  private final SavingsGoalService savingsGoalService;
  private final SavingsGoalMapper savingsGoalMapper;

  public UserChallengeService(
      ChallengeRepository<T> challengeRepository,
      UserRepository userRepository,
      ChatGPTService chatGPTService,
      UserMapper userMapper,
      ChallengeMapper challengeMapper,
      SavingsGoalMapper savingsGoalMapper,
      SavingsGoalService savingsGoalService) {
    this.challengeRepository = challengeRepository;
    this.userRepository = userRepository;
    this.chatGPTService = chatGPTService;
    this.userMapper = userMapper;
    this.challengeMapper = challengeMapper;
    this.savingsGoalService = savingsGoalService;
    this.savingsGoalMapper = savingsGoalMapper;
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

    challengeToRemove.getUsers().remove(user);
    challengeRepository.save(challengeToRemove);

    return userMapper.toDto(user);
  }

  /**
   * Get a sorted list of challenges for the user.
   *
   * @param userId the id of the user.
   * @return a list of sorted challenges.
   */
  @Transactional(readOnly = true)
  public List<ChallengeDto> getSortedChallengesByUser(Long userId) {
    List<ChallengeDto> allChallenges = new ArrayList<>();

    // Fetch challenges for the user
    List<ConsumptionChallengeDto> consumptionChallenges = fetchConsumptionChallengesForUser(userId);
    List<PurchaseChallengeDto> purchaseChallenges = fetchPurchaseChallengesForUser(userId);
    List<SavingChallengeDto> savingChallenges = fetchSavingChallengesForUser(userId);

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
   * Get active sorted challenges
   *
   * @param userId the id of the user.
   * @return a list of active sorted challenges.
   */
  public List<ChallengeDto> getActiveSortedChallengesByUser(Long userId) {
    List<ChallengeDto> allChallenges = getSortedChallengesByUser(userId);
    return allChallenges.stream().filter(challenge -> !challenge.isCompleted()).toList();
  }

  /**
   * Get consumption challenges for a user.
   *
   * @param userId the id of the user.
   * @return a list of consumption challenges for the user.
   */
  public List<ConsumptionChallengeDto> fetchConsumptionChallengesForUser(Long userId) {
    User user =
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

    return user.getChallenges().stream()
        .filter(ConsumptionChallenge.class::isInstance)
        .map(challengeMapper::toDto)
        .map(ConsumptionChallengeDto.class::cast)
        .toList();
  }

  /**
   * Get purchase challenges for a user.
   *
   * @param userId the id of the user.
   * @return a list of purchase challenges for the user.
   */
  public List<PurchaseChallengeDto> fetchPurchaseChallengesForUser(Long userId) {
    User user =
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

    return user.getChallenges().stream()
        .filter(PurchaseChallenge.class::isInstance)
        .map(challengeMapper::toDto)
        .map(PurchaseChallengeDto.class::cast)
        .toList();
  }

  /**
   * Get saving challenges for a user.
   *
   * @param userId the id of the user.
   * @return a list of saving challenges for the user.
   */
  public List<SavingChallengeDto> fetchSavingChallengesForUser(Long userId) {
    User user =
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

    return user.getChallenges().stream()
        .filter(SavingChallenge.class::isInstance)
        .map(challengeMapper::toDto)
        .map(SavingChallengeDto.class::cast)
        .toList();
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

    return userMapper.toDto(user);
  }

  /**
   * Generate a chat response for a user.
   *
   * @param userInfo user information for the user.
   * @return a challenge DTO for the user.
   */
  public ChallengeDto generateChatResponse(UserInfoDto userInfo) {
    // Construct the array of messages for the conversation
    Map<String, String>[] messages = new Map[3];
    ChallengeParser challengeParser = new ChallengeParser();
    ChallengeGenerator challengeGenerator = new ChallengeGenerator();

    // Add system message indicating the assistant's role
    Map<String, String> systemMessage = new HashMap<>();
    systemMessage.put("role", "system");
    systemMessage.put("content", "You are a helpful assistant.");
    messages[0] = systemMessage;

    // Add user message with user information and challenge type selection
    Map<String, String> userInfoMessage = new HashMap<>();
    userInfoMessage.put("role", "user");
    userInfoMessage.put(
        "content",
        "Opprett en utfordring for brukeren med følgende informasjon:\n\n"
            + "- Inntekt: "
            + userInfo.getIncome()
            + "\n"
            + "- Yrkesstatus: "
            + userInfo.getOccupationStatus()
            + "\n"
            + "- Motivasjons nivå: "
            + userInfo.getMotivation()
            + "\n"
            + "- Steder som vil spares på: "
            + userInfo.getBudgetingLocations()
            + "\n"
            + "- Produkter som vil spares på: "
            + userInfo.getBudgetingProducts()
            + "\n\n"
            + "Velg en av følgende typer utfordringer: Saving, Purchase.");
    messages[1] = userInfoMessage;

    // Add assistant message with instructions for each challenge type
    Map<String, String> assistantMessage = new HashMap<>();
    assistantMessage.put("role", "assistant");
    assistantMessage.put(
        "content",
        "Hver utfordring har følgende grunnleggende felt:\n"
            + "1. Title\n"
            + "2. Description\n"
            + "3. Difficulty Level (EASY, MEDIUM, HARD)\n"
            + "4. Time Interval (DAILY, WEEKLY, MONTHLY)\n"
            + "5. Target Amount (i kroner (ikke ta med 'kr' i svaret ditt) eller kjøpsmengde av "
            + "et produkt, datatype: double\n"
            + "6. Media Url (emoji)\n\n"
            + "De ulike utfordringstypene har spesifikke felt, legg til feltet for den valgte "
            + "utfordringstypen:\n"
            + "1. Saving Challenge:\n"
            + "   - Kun feltene nevnt ovenfor.\n"
            + "2. Purchase Challenge:\n"
            + "   - Product Name:\n\n"
            + " - Product Price:"
            + "Gi realistiske utfordringer! Ikke glem å inkludere emoji for å gjøre det morsomt!\n\n"
            + "Gi realistiske verdier for utfordringene, med tanke på varighet (Time interval), "
            + "Target amount og vanskelighetsgrad.\n "
            + "Du skal skrive utfordringene i dette formatet:\n"
            + "Challenge Type: <Utfordringstype>\n"
            + "Title: <Tittel>\n"
            + "Description: <Beskrivelse>\n"
            + "..."
            + "Gi kun utfordringen i dette formatet, ikke noe mer. Gi kun én utfordring om"
            + "gangen!!! Så det siste du skal skrive er 'Media Url: <Emoji>'. Tittel og "
            + "beskrivelse skal være på Norsk bokmål og utfordringen skal være realistisk! Ikke "
            + "nevn antall kroner / kjøpsmengde i 'Description, kun i Target Amount."
            + "Lag enten en Saving eller en Purchase challenge, husk 'Produt name' hvis du "
            + "velger purchase challenge. Ta for deg ETT produkt om gangen når du lager en  "
            + "utfordring!, prøv å "
            + "lag forskjellige utfordringer & bruk forskjellig utfordringstype, og husk hver gang "
            + "realistiske verdier mtp. varighet, vanskelighetsgrad og målbeløp. Varier mellom Saving Challenge og Purchase Challenge for hver gang du lager en ny utfordring :)"
            + "Target amount kan ikke være 0! Må være høyere enn 0, gi et rimelig tall med tanke "
            + "på utfordringen. Ikke bruk samme produkt hver gang! Ikke bruk samme utfordringstype"
            + "hver gang! Så ikke lag to saving challenges etter hverandre, eller to purchase "
            + "challenges etter hverandre, eller to kaffe-utfordringer etter hverandre. Varier! "
            + ":) Det må være en korrelasjon mellom utfordringen og brukerens informasjon. "
            + "Det må også være sammenheng mellom beskrivelse, tittel, målbeløp og TimeInterval!"
            + "Så ikke si 'Reduser daglig kaffe', hvis utfordringen er WEEKLY for eksempel. "
            + "IKKE BRUK SAMME PRODUKT HVER GANG! IKKE BRUK SAMME UTFORDRINGSTYPE HVER GANG!");
    messages[2] = assistantMessage;

    // Send messages to the ChatGPT API and get completion
    String completion;
    try {
      completion = chatGPTService.sendMessagesAndGetCompletion(messages);
    } catch (Exception e) {
      return challengeGenerator.randomChallengeGenerator(userInfo);
    }
    // Parse the completion to a ChallengeDTO
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      JsonNode jsonResponse = objectMapper.readTree(completion);
      String text = jsonResponse.get("choices").get(0).get("message").get("content").asText();
      return challengeParser.parse(text);
    } catch (Exception e) {
      e.printStackTrace();
      return challengeGenerator.randomChallengeGenerator(userInfo);
    }
  }

  /**
   * Generate a random challenge for a user.
   *
   * @param userInfo user information for the user.
   * @return a challenge DTO for the user.
   */
  public ChallengeDto generateRandomChallenge(UserInfoDto userInfo) {
    ChallengeGenerator challengeGenerator = new ChallengeGenerator();
    return challengeGenerator.randomChallengeGenerator(userInfo);
  }

  /**
   * Checks if the user has completed N number of challenges.
   *
   * @param userId the id of the user.
   * @param numberOfChallenges the number of challenges to check for.
   */
  public boolean hasNumberOfCompletedChallenges(Long userId, int numberOfChallenges) {
    User user =
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

    List<ChallengeDto> challenges = getSortedChallengesByUser(userId);
    int completedChallenges = 0;

    for (ChallengeDto challenge : challenges) {
      Challenge challengeEntity = challengeMapper.toEntity(challenge);
      if (challengeEntity.isCompleted()
          && (challengeEntity.getTargetAmount() >= challengeEntity.getUsedAmount())) {
        completedChallenges++;
      }
    }

    return completedChallenges >= numberOfChallenges;
  }

  /**
   * Update completed based on whether the target has been reached or not.
   *
   * @param challengeId the id of the challenge.
   * @param userId the id of the user.
   * @throws NotFoundException if the challenge is not found.
   * @return true if the challenge was completed, false otherwise.
   */
  public boolean updateCompleted(Long userId, Long challengeId) {
    // Retrieve the user entity
    User user =
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

    // Get active sorted challenges for the user
    List<ChallengeDto> activeChallenges = getSortedChallengesByUser(userId);

    // Find the challenge instance by its ID in the active challenges list
    Optional<ChallengeDto> optionalChallenge =
        activeChallenges.stream()
            .filter(challenge -> challenge.getId().equals(challengeId))
            .findFirst();

    // Check if the challenge with the given ID exists
    if (optionalChallenge.isPresent()) {
      ChallengeDto challengeDto = optionalChallenge.get();
      double amountToAdd = calculateAmountToAdd(challengeDto);

      // Check if the challenge has expired and if the challenge isnt a saving challenge with
      // completed amount
      if (challengeDto.getExpiryDate().isAfter(LocalDate.now())
          && !(challengeDto instanceof SavingChallengeDto
              && Objects.equals(challengeDto.getUsedAmount(), challengeDto.getTargetAmount()))) {
        return false;
      }

      if (amountToAdd > 0) {
        challengeDto.setCompleted(true);

        // If target amount has been reached, add remaining to savings goal.
        try {
          SavingsGoalDto currentSavingsGoalDto = savingsGoalService.getCurrentSavingsGoal(userId);
          Long savingGoalId = currentSavingsGoalDto.getId();

          savingsGoalService.updateSavedAmount(userId, savingGoalId, amountToAdd);
        } catch (NotFoundException e) {
          e.printStackTrace();
        }
      } else {
        challengeDto.setCompleted(false);
      }

      removeChallengeFromUser(userId, challengeId);
      challengeRepository.deleteById(challengeId);
      userRepository.save(user);
      return true;
    } else {
      return false;
    }
  }

  private double calculateAmountToAdd(ChallengeDto challengeDto) {
    if (challengeDto instanceof PurchaseChallengeDto) {
      PurchaseChallengeDto purchaseChallengeDto = (PurchaseChallengeDto) challengeDto;
      return (purchaseChallengeDto.getTargetAmount() - purchaseChallengeDto.getUsedAmount())
          * purchaseChallengeDto.getProductPrice();
    } else if (challengeDto instanceof SavingChallengeDto) {
      return challengeDto.getUsedAmount();
    } else {
      return challengeDto.getTargetAmount() - challengeDto.getUsedAmount();
    }
  }
}
