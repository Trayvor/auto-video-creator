package org.creator.autovideocreator.dto.impl;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.creator.autovideocreator.dto.CreateDto;

public record CreateProjectDto(@NotBlank String name) implements CreateDto {
}
