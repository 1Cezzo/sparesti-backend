package edu.ntnu.idi.stud.team10.sparesti.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import edu.ntnu.idi.stud.team10.sparesti.dto.UserDto;
import edu.ntnu.idi.stud.team10.sparesti.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(target = "password", ignore = true)
  UserDto toDto(User user);

  User toEntity(UserDto userDto);
}
