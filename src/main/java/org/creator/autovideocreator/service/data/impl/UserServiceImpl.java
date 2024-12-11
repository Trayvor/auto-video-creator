package org.creator.autovideocreator.service.data.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.creator.autovideocreator.dto.user.CreateUserDto;
import org.creator.autovideocreator.dto.user.ResponseUserDto;
import org.creator.autovideocreator.exception.UserAlreadyExistsException;
import org.creator.autovideocreator.mapper.UserMapper;
import org.creator.autovideocreator.model.User;
import org.creator.autovideocreator.repository.UserRepository;
import org.creator.autovideocreator.service.data.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public ResponseUserDto save(CreateUserDto createUserDto) {
        if (userRepository.findByTelegramId(createUserDto.telegramId()).isPresent()) {
            throw new UserAlreadyExistsException(
                    "User with telegram id " + createUserDto.telegramId() + " already exists");
        }
        User user = userMapper.toModel(createUserDto);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public ResponseUserDto getByTelegramId(String telegramId) {
        User user = userRepository.findByTelegramId(telegramId).orElseThrow(
                () -> new EntityNotFoundException("User with telegram id " + telegramId + " not found")
        );
        return userMapper.toDto(user);
    }
}
