package edu.ntnu.idi.stud.team10.sparesti.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

/** A PurchaseChallenge is a Challenge that requires the user to purchase a certain product. */
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class PurchaseChallenge extends Challenge {
  @Column(name = "product_name", nullable = false)
  private String productName;

  @Column(name = "product_price", nullable = false)
  private double productPrice;
}
