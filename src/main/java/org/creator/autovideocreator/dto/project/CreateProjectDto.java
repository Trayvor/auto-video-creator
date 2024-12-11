package org.creator.autovideocreator.dto.project;

import jakarta.validation.constraints.NotBlank;
import org.creator.autovideocreator.dto.tag.CreateTagDto;
import org.creator.autovideocreator.dto.upload.time.CreateUploadTimeDto;

import java.util.List;

public record CreateProjectDto(@NotBlank String name, String anotherVideo,
                               List<CreateUploadTimeDto> uploadTimes,
                               List<CreateTagDto> tags, Boolean isOverlay,
                               String telegramId, Integer videosDuration, String timezoneId) {
}
