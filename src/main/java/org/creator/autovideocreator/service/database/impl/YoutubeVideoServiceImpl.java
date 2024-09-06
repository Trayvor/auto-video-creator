package org.creator.autovideocreator.service.database.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.creator.autovideocreator.model.Project;
import org.creator.autovideocreator.model.YoutubeVideo;
import org.creator.autovideocreator.model.constant.YoutubeVideoStatus;
import org.creator.autovideocreator.repository.ProjectRepository;
import org.creator.autovideocreator.repository.YoutubeVideoRepository;
import org.creator.autovideocreator.service.database.YoutubeVideoService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class YoutubeVideoServiceImpl implements YoutubeVideoService {
    private final YoutubeVideoRepository youtubeVideoRepository;
    private final ProjectRepository projectRepository;
    @Override
    public YoutubeVideo changeStatus(Long id, YoutubeVideoStatus status) {
        YoutubeVideo youtubeVideo = youtubeVideoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can`t find internal video with id "
                        + id)
        );
        youtubeVideo.setStatus(status.toString());
        youtubeVideoRepository.save(youtubeVideo);
        return youtubeVideo;
    }

    @Override
    @Transactional
    public YoutubeVideo createAndAddYoutubeVideo(Long projectId, String videoId) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Can`t find project with id " + projectId)
        );
        YoutubeVideo youtubeVideo = new YoutubeVideo();
        youtubeVideo.setProject(project);
        youtubeVideo.setVideoId(videoId);
        youtubeVideo.setStatus(YoutubeVideoStatus.TO_CROP.toString());
        project.getYoutubeVideos().add(youtubeVideo);
        Project savedProject = projectRepository.save(project);
        return savedProject.getYoutubeVideos().getLast();
    }
}
