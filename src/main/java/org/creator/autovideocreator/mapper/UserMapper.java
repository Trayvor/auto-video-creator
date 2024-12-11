package org.creator.autovideocreator.mapper;

import org.creator.autovideocreator.configuration.MapperConfig;
import org.creator.autovideocreator.dto.user.CreateUserDto;
import org.creator.autovideocreator.dto.user.ResponseUserDto;
import org.creator.autovideocreator.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    ResponseUserDto toDto(User user);

    User toModel(CreateUserDto dto);
}
