package org.creator.autovideocreator.service.data;

import org.creator.autovideocreator.dto.user.CreateUserDto;
import org.creator.autovideocreator.dto.user.ResponseUserDto;

public interface UserService {
    ResponseUserDto save(CreateUserDto createUserDto);

    ResponseUserDto getByTelegramId(String telegramId);
}
