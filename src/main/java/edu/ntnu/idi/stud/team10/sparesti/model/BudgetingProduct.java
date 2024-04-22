package edu.ntnu.idi.stud.team10.sparesti.model;

import edu.ntnu.idi.stud.team10.sparesti.enums.TimeInterval;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BudgetingProduct {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private TimeInterval frequency;

  @Min(1)
  @Column(nullable = false)
  private Integer amount;

  @ManyToOne private UserInfo userInfo;
}
