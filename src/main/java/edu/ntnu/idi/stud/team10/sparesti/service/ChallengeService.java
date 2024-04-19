package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;

/** Service for Challenge entities. */
@Service
public class ChallengeService {

  /**
   * Calculate the expiry date of a challenge based on the time interval.
   *
   * @param timeInterval the time interval of the challenge.
   * @return (LocalDate) The expiry date of the challenge.
   */
  public LocalDate calculateExpiryDate(TimeInterval timeInterval) {
    LocalDate currentDate = LocalDate.now();
    return switch (timeInterval) {
      case DAILY -> currentDate.plusDays(1);
      case WEEKLY -> currentDate.plusWeeks(1);
      case MONTHLY -> currentDate.plusMonths(1);
      default -> currentDate;
    };
  }
}
