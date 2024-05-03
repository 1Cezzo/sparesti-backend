package edu.ntnu.idi.stud.team10.sparesti.mapper;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetRowDto;
import edu.ntnu.idi.stud.team10.sparesti.model.BudgetRow;

/** Mapper between budget row entity and budget row dto. */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BudgetRowMapper {
  BudgetRowMapper INSTANCE = Mappers.getMapper(BudgetRowMapper.class);

  @Mapping(target = "budget", ignore = true)
  BudgetRow toEntity(BudgetRowDto budgetRowDto);

  BudgetRowDto toDto(BudgetRow budgetRow);

  /**
   * Update budget row entity from dto.
   *
   * @param budgetRowDto
   * @param budgetRow
   */
  default void updateFromDto(BudgetRowDto budgetRowDto, @MappingTarget BudgetRow budgetRow) {
    Optional.ofNullable(budgetRowDto.getName()).ifPresent(budgetRow::setName);
    Optional.ofNullable(budgetRowDto.getUsedAmount()).ifPresent(budgetRow::setUsedAmount);
    Optional.ofNullable(budgetRowDto.getMaxAmount()).ifPresent(budgetRow::setMaxAmount);
    Optional.ofNullable(budgetRowDto.getCategory()).ifPresent(budgetRow::setCategory);
    Optional.ofNullable(budgetRowDto.getEmoji()).ifPresent(budgetRow::setEmoji);
  }
}
