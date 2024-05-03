package edu.ntnu.idi.stud.team10.sparesti.model;

import jakarta.persistence.Entity;
import lombok.*;

/** A SavingChallenge is a Challenge that requires the user to save a certain amount of money. */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Entity
@Data
public class SavingChallenge extends Challenge {}
