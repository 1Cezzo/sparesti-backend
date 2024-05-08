package edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ntnu.idi.stud.team10.sparesti.model.SavingTip;
import edu.ntnu.idi.stud.team10.sparesti.repository.SavingTipRepository;

@Service
/** Simple service for getting a random saving tip */
public class SavingTipService {
  private final SavingTipRepository savingTipRepository;

  /**
   * Constructs a SavingTipService with the necessary repository.
   *
   * @param savingTipRepository Repository for accessing saving tip data.
   */
  @Autowired
  public SavingTipService(SavingTipRepository savingTipRepository) {
    this.savingTipRepository = savingTipRepository;
  }

  // Could move everything related to SavingTips into one file for simplicity.
  // Currently following the practice we have been following until now,
  // so I am splitting it up into service and controller layer.

  /**
   * Get a random savings tip.
   *
   * @return (String): A random savings tip in the repository.
   */
  public String getRandomSavingTip() {
    List<SavingTip> allTips = savingTipRepository.findAll();
    if (allTips.isEmpty()) {
      return "No saving tips found";
    } else {
      SavingTip randomTip = getRandomElement(allTips);
      return randomTip.getMessage();
    }
  }

  /**
   * Get a random element from a list.
   *
   * @param list (List<SavingTip>): The list to get a random element from.
   * @return (SavingTip): A random element from the list.
   */
  private SavingTip getRandomElement(List<SavingTip> list) {
    Random rand = new Random();
    return list.get(rand.nextInt(list.size()));
  }

  /**
   * Returns whether the database of saving tips is empty.
   *
   * @return (boolean): true if there are no saving tips, false otherwise.
   */
  public boolean noSavingTips() {
    // used for checking if the backend database needs saving tips input into it
    return savingTipRepository.count() == 0;
  }

  /**
   * Create and store a new saving tip-
   *
   * @param message (String): The message being told by the saving tip.
   * @throws IllegalArgumentException if the message is null or empty.
   */
  public void createSavingTip(String message) {
    if (message == null || message.isEmpty()) {
      throw new IllegalArgumentException("Message cannot be null or empty");
    }
    SavingTip savingTip = new SavingTip();
    savingTip.setMessage(message);
    savingTipRepository.save(savingTip);
  }
}
