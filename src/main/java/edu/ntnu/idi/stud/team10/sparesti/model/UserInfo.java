package edu.ntnu.idi.stud.team10.sparesti.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import edu.ntnu.idi.stud.team10.sparesti.enums.OccupationStatus;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Hidden
  private Long id;

  @PrimaryKeyJoinColumn() @OneToOne private User user;

  @Column() private String firstName;

  @Column() private String lastName;

  @Column() private LocalDate dateOfBirth;

  @Column() private OccupationStatus occupationStatus;

  @Min(0)
  @Max(5)
  @Column()
  private Integer motivation;

  @Min(0)
  @Column()
  private Integer income;

  @OneToMany(mappedBy = "userInfo")
  private Set<BudgetingProduct> budgetingProducts;

  @ElementCollection(targetClass = String.class)
  @CollectionTable(
      name = "budgeting_locations",
      joinColumns = @JoinColumn(name = "user_details_id"))
  @Column(name = "location")
  private List<String> budgetingLocations;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserInfo userInfo = (UserInfo) o;
    return Objects.equals(id, userInfo.id)
        && Objects.equals(user, userInfo.user)
        && Objects.equals(firstName, userInfo.firstName)
        && Objects.equals(lastName, userInfo.lastName)
        && Objects.equals(dateOfBirth, userInfo.dateOfBirth)
        && occupationStatus == userInfo.occupationStatus
        && Objects.equals(motivation, userInfo.motivation)
        && Objects.equals(income, userInfo.income);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id, user, firstName, lastName, dateOfBirth, occupationStatus, motivation, income);
  }

  @Override
  public String toString() {
    return "UserInfo{"
        + "id="
        + id
        + ", user="
        + user
        + ", firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", dateOfBirth="
        + dateOfBirth
        + ", occupationStatus="
        + occupationStatus
        + ", motivation="
        + motivation
        + ", income="
        + income
        + '}';
  }
}
