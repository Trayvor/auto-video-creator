package org.creator.autovideocreator.service.database;

import org.creator.autovideocreator.dto.impl.CreateProjectDto;
import org.creator.autovideocreator.dto.impl.CreateUserDto;
import org.creator.autovideocreator.dto.impl.ProjectDto;
import org.creator.autovideocreator.dto.impl.UpdateUserDto;
import org.creator.autovideocreator.dto.impl.UserDto;

import java.util.List;

public interface UserService {
    UserDto getById(Long id);
    List<UserDto> findAll();
    UserDto save(CreateUserDto entity);
    void deleteById(Long id);
    UserDto updateById(Long id, UpdateUserDto updateUserDto);
}
