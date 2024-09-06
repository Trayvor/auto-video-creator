package org.creator.autovideocreator.mapper;

import org.creator.autovideocreator.configuration.MapperConfig;
import org.creator.autovideocreator.dto.impl.CreateUserDto;
import org.creator.autovideocreator.dto.impl.UpdateUserDto;
import org.creator.autovideocreator.dto.impl.UserDto;
import org.creator.autovideocreator.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserDto toDto(User user);
    User toModel(UserDto userDto);
    User toModel(CreateUserDto userDto);
    void updateUser(UpdateUserDto updateUserDto, @MappingTarget User user);
}
