package edu.ntnu.idi.stud.team10.sparesti.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import edu.ntnu.idi.stud.team10.sparesti.dto.UserInfoDto;
import edu.ntnu.idi.stud.team10.sparesti.model.UserInfo;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    uses = BudgetingProductMapper.class)
public interface UserInfoMapper {
  UserInfoMapper INSTANCE = Mappers.getMapper(UserInfoMapper.class);

  @Mapping(source = "budgetingProducts", target = "budgetingProducts")
  @Mapping(target = "id", ignore = true)
  UserInfo toEntity(UserInfoDto userInfoDto);

  @Mapping(source = "budgetingProducts", target = "budgetingProducts")
  @Mapping(target = "userId", source = "user.id")
  UserInfoDto toDto(UserInfo userInfo);
}
