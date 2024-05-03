package edu.ntnu.idi.stud.team10.sparesti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ntnu.idi.stud.team10.sparesti.model.Challenge;

/** Repository for Challenge entities. */
@Repository
public interface ChallengeRepository<T extends Challenge> extends JpaRepository<T, Long> {}
