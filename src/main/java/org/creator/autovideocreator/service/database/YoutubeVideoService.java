package org.creator.autovideocreator.service.database;

import org.creator.autovideocreator.model.YoutubeVideo;
import org.creator.autovideocreator.model.constant.YoutubeVideoStatus;

public interface YoutubeVideoService {
    YoutubeVideo changeStatus(Long id, YoutubeVideoStatus status);
    YoutubeVideo createAndAddYoutubeVideo(Long projectId, String videoId);
}
