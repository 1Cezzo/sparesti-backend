package edu.ntnu.idi.stud.team10.sparesti.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import edu.ntnu.idi.stud.team10.sparesti.dto.SavingsGoalDto;
import edu.ntnu.idi.stud.team10.sparesti.model.SavingsGoal;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SavingsGoalMapper {
  SavingsGoalMapper INSTANCE = Mappers.getMapper(SavingsGoalMapper.class);

  @Mapping(target = "users", ignore = true)
  SavingsGoal toEntity(SavingsGoalDto savingsGoalDto);

  SavingsGoalDto toDto(SavingsGoal savingsGoal);
}
