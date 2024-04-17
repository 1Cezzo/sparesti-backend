package edu.ntnu.idi.stud.team10.sparesti.repository;

import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;

import org.springframework.data.jpa.repository.JpaRepository;

/** Repository for the BudgetRow entity. */
public interface BudgetRowRepository extends JpaRepository<BudgetRow, Long> {}
