package org.creator.autovideocreator.service.data;

import org.creator.autovideocreator.dto.youtube.video.ResponseYoutubeVideoDto;
import org.creator.autovideocreator.dto.youtube.video.SearchYoutubeVideosDto;

import java.util.List;

public interface YoutubeVideoService {
    List<ResponseYoutubeVideoDto> addVideosByQuery(SearchYoutubeVideosDto requestDto);
}
