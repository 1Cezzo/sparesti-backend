package edu.ntnu.idi.stud.team10.sparesti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ntnu.idi.stud.team10.sparesti.model.BudgetingProduct;

/** Repository for BudgetingProduct entities. */
public interface BudgetingProductRepository extends JpaRepository<BudgetingProduct, Long> {}
