package org.creator.autovideocreator.dto.youtube.video;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SearchYoutubeVideosDto(@NotBlank String query, @NotNull Integer numberOfResults,
                                     @NotBlank String projectName, @NotBlank String telegramId) {
}
