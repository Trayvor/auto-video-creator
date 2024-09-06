package org.creator.autovideocreator.dto.impl;

import jakarta.validation.constraints.NotBlank;
import org.creator.autovideocreator.dto.CreateDto;

public record CreateAccountDto(@NotBlank String securityToken,
                               @NotBlank String accountType) implements CreateDto {
}
