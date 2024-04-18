package edu.ntnu.idi.stud.team10.sparesti.model;

import jakarta.persistence.Entity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Entity
@Data
public class SavingChallenge extends Challenge {}
