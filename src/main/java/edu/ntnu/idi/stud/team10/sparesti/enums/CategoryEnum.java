package edu.ntnu.idi.stud.team10.sparesti.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/** Enum representing the different categories that a budget row or transaction can have. */
public enum CategoryEnum {
  GROCERIES,
  TRANSPORTATION,
  ENTERTAINMENT,
  CLOTHING,
  UTILITIES,
  OTHER;

  private static final List<CategoryEnum> VALUES =
          List.of(values());
  private static final int SIZE = VALUES.size();
  private static final Random RANDOM = new Random();

  public static CategoryEnum getRandomCategory() {
    //return VALUES.get(ThreadLocalRandom.current().nextInt(SIZE)); //could also work?
    return VALUES.get(RANDOM.nextInt(SIZE));
  }
}
