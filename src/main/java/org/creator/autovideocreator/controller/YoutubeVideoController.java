package org.creator.autovideocreator.controller;

import lombok.RequiredArgsConstructor;
import org.creator.autovideocreator.dto.youtube.video.ResponseYoutubeVideoDto;
import org.creator.autovideocreator.dto.youtube.video.SearchYoutubeVideosDto;
import org.creator.autovideocreator.service.data.YoutubeVideoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/youtube-video")
public class YoutubeVideoController {
    private final YoutubeVideoService youtubeVideoService;

    @PostMapping("/search-by-query")
    public List<ResponseYoutubeVideoDto> addVideosByQuery(@RequestBody SearchYoutubeVideosDto searchYoutubeVideosDto) {
        return youtubeVideoService.addVideosByQuery(searchYoutubeVideosDto);
    }
}
