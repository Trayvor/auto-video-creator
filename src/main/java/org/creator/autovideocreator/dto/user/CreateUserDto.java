package org.creator.autovideocreator.dto.user;

import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(@NotBlank String telegramId) {
}
