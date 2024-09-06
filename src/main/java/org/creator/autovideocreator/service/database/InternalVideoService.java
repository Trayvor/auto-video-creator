package org.creator.autovideocreator.service.database;

import org.creator.autovideocreator.model.InternalVideo;
import org.creator.autovideocreator.model.constant.InternalVideoStatus;

public interface InternalVideoService {
    InternalVideo changeStatus(Long id, InternalVideoStatus status);
    InternalVideo createAndAddInternalVideo(Long projectId);
}
