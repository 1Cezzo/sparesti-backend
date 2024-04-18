package edu.ntnu.idi.stud.team10.sparesti.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;

@Service
public class ChallengeService {

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
