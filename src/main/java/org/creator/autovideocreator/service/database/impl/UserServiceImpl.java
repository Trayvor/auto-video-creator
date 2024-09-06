package org.creator.autovideocreator.service.database.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.creator.autovideocreator.dto.impl.CreateProjectDto;
import org.creator.autovideocreator.dto.impl.CreateUserDto;
import org.creator.autovideocreator.dto.impl.ProjectDto;
import org.creator.autovideocreator.dto.impl.UpdateUserDto;
import org.creator.autovideocreator.dto.impl.UserDto;
import org.creator.autovideocreator.mapper.ProjectMapper;
import org.creator.autovideocreator.mapper.UserMapper;
import org.creator.autovideocreator.model.Project;
import org.creator.autovideocreator.model.User;
import org.creator.autovideocreator.repository.UserRepository;
import org.creator.autovideocreator.service.database.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectMapper projectMapper;

    @Override
    public UserDto getById(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can`t find user with id " + id)));
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserDto save(CreateUserDto entity) {
        return userMapper.toDto(userRepository.save(userMapper.toModel(entity)));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto updateById(Long id, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can`t find user with id " + id));
        userMapper.updateUser(updateUserDto, user);
        userRepository.save(user);
        return userMapper.toDto(user);
    }
}
