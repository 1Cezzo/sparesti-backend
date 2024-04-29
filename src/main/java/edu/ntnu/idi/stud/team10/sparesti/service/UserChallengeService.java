package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ntnu.idi.stud.team10.sparesti.dto.*;
import edu.ntnu.idi.stud.team10.sparesti.model.*;
import edu.ntnu.idi.stud.team10.sparesti.repository.ChallengeRepository;
import edu.ntnu.idi.stud.team10.sparesti.repository.UserRepository;
import edu.ntnu.idi.stud.team10.sparesti.util.NotFoundException;
import edu.ntnu.idi.stud.team10.sparesti.util.ChallengeGenerator;
import edu.ntnu.idi.stud.team10.sparesti.util.ChallengeParser;

/**
 * Service for User Challenge entities.
 *
 * @param <T> the type of the challenge.
 */
@Service
public class UserChallengeService<T extends Challenge> {

  private final ChallengeRepository<T> challengeRepository;
  private final UserRepository userRepository;
  private UserInfoService userInfoService;
  private ChatGPTService chatGPTService;

  public UserChallengeService(
      ChallengeRepository<T> challengeRepository,
      UserRepository userRepository,
      UserInfoService userInfoService,
      ChatGPTService chatGPTService) {
    this.challengeRepository = challengeRepository;
    this.userRepository = userRepository;
    this.userInfoService = userInfoService;
    this.chatGPTService = chatGPTService;
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
    Collections.sort(
        allChallenges,
        new Comparator<ChallengeDto>() {
          @Override
          public int compare(ChallengeDto a, ChallengeDto b) {
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
  public List<ConsumptionChallengeDto> fetchConsumptionChallengesForUser(Long userId) {
    User user =
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));

    return user.getChallenges().stream()
        .filter(challenge -> challenge instanceof ConsumptionChallenge)
        .map(challenge -> new ConsumptionChallengeDto((ConsumptionChallenge) challenge))
        .collect(Collectors.toList());
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
        .filter(challenge -> challenge instanceof PurchaseChallenge)
        .map(challenge -> new PurchaseChallengeDto((PurchaseChallenge) challenge))
        .collect(Collectors.toList());
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
        .filter(challenge -> challenge instanceof SavingChallenge)
        .map(challenge -> new SavingChallengeDto((SavingChallenge) challenge))
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
            + "   - Product Name\n\n"
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
            + "realistiske verdier mtp. varighet, vanskelighetsgrad og målbeløp.");
    messages[2] = assistantMessage;

    // Send messages to the ChatGPT API and get completion
    String completion = chatGPTService.sendMessagesAndGetCompletion(messages);
    System.out.println(completion);

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
}
