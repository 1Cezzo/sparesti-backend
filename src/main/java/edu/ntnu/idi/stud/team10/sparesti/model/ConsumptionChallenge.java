package edu.ntnu.idi.stud.team10.sparesti.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class ConsumptionChallenge extends Challenge {
  @Column(name = "product_category", nullable = false)
  private String productCategory;

  @Column(name = "reduction_percentage", nullable = false)
  private double reductionPercentage;
}
