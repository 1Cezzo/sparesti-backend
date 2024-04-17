package edu.ntnu.idi.stud.team10.sparesti.model;

import java.time.LocalDate;
import java.util.HashSet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;
import java.util.Set;


/**
 * A savings goal is a goal that a user sets for themselves to save up for. It has a name, a target
 * amount, and a deadline.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Data
@Table(name = "budget")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "budget")
    private Set<BudgetRow> row = new HashSet<>();


}
