package edu.ntnu.idi.stud.team10.sparesti.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import edu.ntnu.idi.stud.team10.sparesti.dto.TransactionBudgetRowDto;
import edu.ntnu.idi.stud.team10.sparesti.model.TransactionBudgetRow;

/** Mapper between transaction budget row entity and transaction budget row dto. */
@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {TransactionMapper.class})
public interface TransactionBudgetRowMapper {
  TransactionBudgetRowMapper INSTANCE = Mappers.getMapper(TransactionBudgetRowMapper.class);

  @Mapping(target = "budgetRow", source = "budgetRow")
  @Mapping(target = "transactions", source = "transactions")
  TransactionBudgetRowDto toDto(TransactionBudgetRow transactionBudgetRow);

  @Mapping(target = "budgetRow", source = "budgetRow")
  @Mapping(target = "transactions", source = "transactions")
  TransactionBudgetRow toEntity(TransactionBudgetRowDto transactionBudgetRowDto);
}
