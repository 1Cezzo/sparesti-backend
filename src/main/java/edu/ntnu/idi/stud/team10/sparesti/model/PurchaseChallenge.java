package edu.ntnu.idi.stud.team10.sparesti.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class PurchaseChallenge extends Challenge {
  @Column(name = "product_name", nullable = false)
  private String productName;
}
