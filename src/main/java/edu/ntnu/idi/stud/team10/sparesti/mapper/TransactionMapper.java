package edu.ntnu.idi.stud.team10.sparesti.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import edu.ntnu.idi.stud.team10.sparesti.dto.TransactionDto;
import edu.ntnu.idi.stud.team10.sparesti.model.Transaction;

/** Mapper between transaction entity and transaction dto. */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {
  TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

  @Mapping(target = "account", ignore = true)
  Transaction toEntity(TransactionDto transactionDto);

  @Mapping(target = "accountNr", source = "account.accountNr")
  TransactionDto toDto(Transaction transaction);
}
