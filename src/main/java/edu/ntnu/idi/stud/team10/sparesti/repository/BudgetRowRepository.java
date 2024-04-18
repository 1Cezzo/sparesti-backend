package edu.ntnu.idi.stud.team10.sparesti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;

/** Repository for the BudgetRow entity. */
public interface BudgetRowRepository extends JpaRepository<BudgetRow, Long> {}
