package org.creator.autovideocreator.service.data.impl;

import com.google.api.services.youtube.model.SearchResult;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.creator.autovideocreator.dto.youtube.video.ResponseYoutubeVideoDto;
import org.creator.autovideocreator.dto.youtube.video.SearchYoutubeVideosDto;
import org.creator.autovideocreator.mapper.YoutubeVideoMapper;
import org.creator.autovideocreator.model.Project;
import org.creator.autovideocreator.model.YoutubeVideo;
import org.creator.autovideocreator.repository.ProjectRepository;
import org.creator.autovideocreator.repository.YoutubeVideoRepository;
import org.creator.autovideocreator.service.data.YoutubeVideoService;
import org.creator.autovideocreator.tool.YouTubeVideoSearchResultFetcherTool;
import org.creator.autovideocreator.tool.YouTubeVideoSearchResultFetcherTool.VideoInfo;
import org.creator.autovideocreator.tool.YoutubeVideoSearchTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class YoutubeVideoServiceImpl implements YoutubeVideoService {
    private final ProjectRepository projectRepository;
    private final YoutubeVideoRepository youtubeVideoRepository;
    private final YoutubeVideoMapper youtubeVideoMapper;

    @Transactional
    @Override
    public List<ResponseYoutubeVideoDto> addVideosByQuery(SearchYoutubeVideosDto requestDto) {
        Project project = projectRepository.findByNameAndUserTelegramId(requestDto.projectName(),
                requestDto.telegramId()).orElseThrow(
                () -> new EntityNotFoundException("Project "
                        + "with name " + requestDto.projectName()
                        + " not found for user with telegram id "
                        + requestDto.telegramId()));

        List<SearchResult> resultList = YoutubeVideoSearchTool.findByName(requestDto.query(),
                requestDto.numberOfResults(), project.getAccounts().get(0).getSecurityToken());

        List<VideoInfo> videoInfoList = YouTubeVideoSearchResultFetcherTool.getVideoDetails(
                resultList, project.getAccounts().get(0).getSecurityToken());

        for (VideoInfo videoInfo : videoInfoList) {
            for (int i = 0; i < videoInfo.durationInSeconds() / project.getVideosDuration(); i++) {
                YoutubeVideo youtubeVideo = new YoutubeVideo()
                        .setVideoId(videoInfo.videoId())
                        .setTitle(videoInfo.title() + " Part " + (i + 1))
                        .setProject(project)
                        .setStartTime(i * project.getVideosDuration())
                        .setEndTime((i + 1) * project.getVideosDuration());
                youtubeVideoRepository.save(youtubeVideo);
                project.getYoutubeVideos().add(youtubeVideo);
            }
        }

        return youtubeVideoMapper.toDto(project.getYoutubeVideos());
    }
}
