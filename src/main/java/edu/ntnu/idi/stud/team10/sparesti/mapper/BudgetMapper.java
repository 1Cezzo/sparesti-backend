package edu.ntnu.idi.stud.team10.sparesti.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetDto;
import edu.ntnu.idi.stud.team10.sparesti.model.Budget;

/** Mapper between budget entity and budget dto. */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {BudgetRowMapper.class})
public interface BudgetMapper {
  BudgetMapper INSTANCE = Mappers.getMapper(BudgetMapper.class);

  @Mapping(target = "row", source = "row")
  Budget toEntity(BudgetDto budgetDto);

  @Mapping(target = "row", source = "row")
  BudgetDto toDto(Budget budget);
}
