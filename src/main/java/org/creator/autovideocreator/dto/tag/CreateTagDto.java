package org.creator.autovideocreator.dto.tag;

import jakarta.validation.constraints.NotBlank;

public record CreateTagDto(@NotBlank String name) {
}
