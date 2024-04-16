package main.java.edu.ntnu.idi.stud.team10.sparesti.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.java.edu.ntnu.idi.stud.team10.sparesti.dto.SavingsGoalDTO;
import main.java.edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;
import main.java.edu.ntnu.idi.stud.team10.sparesti.repository.SavingsGoalRepository;

@Service
public class SavingsGoalService {

  private final SavingsGoalRepository savingsGoalRepository;

  @Autowired
  public SavingsGoalService(SavingsGoalRepository savingsGoalRepository) {
    this.savingsGoalRepository = savingsGoalRepository;
  }

  public SavingsGoal createSavingsGoal(SavingsGoalDTO savingsGoalDTO) {
    SavingsGoal savingsGoal = savingsGoalDTO.toEntity();
    // Perform validation if necessary
    return savingsGoalRepository.save(savingsGoal);
  }

  public List<SavingsGoal> getAllSavingsGoals() {
    return savingsGoalRepository.findAll();
  }

  public Optional<SavingsGoal> getSavingsGoalById(Long id) {
    return savingsGoalRepository.findById(id);
  }

  public SavingsGoal updateSavingsGoal(Long id, SavingsGoalDTO savingsGoalDTO) {
    Optional<SavingsGoal> optionalSavingsGoal = savingsGoalRepository.findById(id);
    if (optionalSavingsGoal.isPresent()) {
      SavingsGoal savingsGoal = optionalSavingsGoal.get();
      savingsGoal.setName(savingsGoalDTO.getName());
      savingsGoal.setTargetAmount(savingsGoalDTO.getTargetAmount());
      savingsGoal.setDeadline(savingsGoalDTO.getDeadline());
      return savingsGoalRepository.save(savingsGoal);
    } else {
      throw new IllegalArgumentException("Savings goal with ID " + id + " not found");
    }
  }

  public void deleteSavingsGoalById(Long id) {
    try {
      savingsGoalRepository.deleteById(id);
    } catch (Exception e) {
      throw new IllegalArgumentException("Savings goal with ID " + id + " not found");
    }
  }
}
