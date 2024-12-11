package org.creator.autovideocreator.dto.project;

import org.creator.autovideocreator.dto.tag.ResponseTagDto;
import org.creator.autovideocreator.dto.upload.time.ResponseUploadTimeDto;
import java.util.List;

public record ResponseProjectDto(Long id, String name, String anotherVideo,
                                 List<ResponseUploadTimeDto> uploadTimes,
                                 List<ResponseTagDto> tags, boolean isOverlay, int videosDuration,
                                 String timezoneId) {
}
