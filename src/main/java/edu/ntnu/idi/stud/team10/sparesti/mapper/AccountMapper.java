package edu.ntnu.idi.stud.team10.sparesti.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import edu.ntnu.idi.stud.team10.sparesti.dto.AccountDto;
import edu.ntnu.idi.stud.team10.sparesti.model.Account;

/** Mapper between account entity and account dto. */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = TransactionMapper.class)
public interface AccountMapper {
  AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

  @Mapping(source = "transactions", target = "transactions")
  Account toEntity(AccountDto accountDto);

  @Mapping(source = "transactions", target = "transactions")
  AccountDto toDto(Account account);
}
