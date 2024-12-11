package org.creator.autovideocreator.mapper;

import org.creator.autovideocreator.configuration.MapperConfig;
import org.creator.autovideocreator.dto.youtube.video.ResponseYoutubeVideoDto;
import org.creator.autovideocreator.model.YoutubeVideo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfig.class)
public interface YoutubeVideoMapper {
    ResponseYoutubeVideoDto toDto(YoutubeVideo youtubeVideo);
    List<ResponseYoutubeVideoDto> toDto(List<YoutubeVideo> youtubeVideoList);
}
