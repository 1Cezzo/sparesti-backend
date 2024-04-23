package edu.ntnu.idi.stud.team10.sparesti.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


/** Enum representing the different categories that a budget row or transaction can have. */
public enum CategoryEnum {
  GROCERIES,
  TRANSPORTATION,
  ENTERTAINMENT,
  CLOTHING,
  UTILITIES,
  OTHER,
  NONE; //for when a user is transferring between their checkings- and savings accounts

  // Filter out the NONE category
  /*
  private static final List<CategoryEnum> VALUES =
          Collections.unmodifiableList(
                  Arrays.stream(values())
                          .filter(category -> category != NONE)
                          .collect(Collectors.toList())
          );
  private static final int SIZE = VALUES.size();
  private static final Random RANDOM = new Random();

  /**
   * Selects a random category (excluding "NONE")
   * for mock data generation.
   *
   * @return a random category enum that isn't "NONE"
   */
  /*
  public static CategoryEnum getRandomCategory() {
    //return VALUES.get(ThreadLocalRandom.current().nextInt(SIZE)); //could also work?
    return VALUES.get(RANDOM.nextInt(SIZE));
  }*/

}
