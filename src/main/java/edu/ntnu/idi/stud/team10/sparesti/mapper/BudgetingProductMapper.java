package edu.ntnu.idi.stud.team10.sparesti.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import edu.ntnu.idi.stud.team10.sparesti.dto.BudgetingProductDto;
import edu.ntnu.idi.stud.team10.sparesti.model.BudgetingProduct;

/** Mapper between budgeting product entity and budgeting product dto. */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BudgetingProductMapper {
  BudgetingProductMapper INSTANCE = Mappers.getMapper(BudgetingProductMapper.class);

  @Mapping(target = "userInfo", ignore = true)
  BudgetingProduct toEntity(BudgetingProductDto budgetingProductDto);

  @Mapping(target = "userInfoId", source = "userInfo.id")
  BudgetingProductDto toDto(BudgetingProduct budgetingProduct);
}
