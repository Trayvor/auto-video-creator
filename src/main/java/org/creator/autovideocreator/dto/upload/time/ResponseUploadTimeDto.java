package org.creator.autovideocreator.dto.upload.time;

import java.time.LocalTime;

public record ResponseUploadTimeDto(Long id, LocalTime timeToUpload) {
}
