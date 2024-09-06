package org.creator.autovideocreator.service.work.Impl;

import lombok.RequiredArgsConstructor;
import org.creator.autovideocreator.dto.impl.ProjectDto;
import org.creator.autovideocreator.exception.NoVideosException;
import org.creator.autovideocreator.model.Account;
import org.creator.autovideocreator.model.InternalVideo;
import org.creator.autovideocreator.model.UploadTime;
import org.creator.autovideocreator.model.YoutubeVideo;
import org.creator.autovideocreator.model.constant.InternalVideoStatus;
import org.creator.autovideocreator.model.constant.YoutubeVideoStatus;
import org.creator.autovideocreator.service.database.InternalVideoService;
import org.creator.autovideocreator.service.database.ProjectService;
import org.creator.autovideocreator.service.tool.VideoDownloadService;
import org.creator.autovideocreator.service.tool.VideoMontageService;
import org.creator.autovideocreator.service.tool.VideoUploadService;
import org.creator.autovideocreator.service.tool.impl.FFmpegVideoMontageService;
import org.creator.autovideocreator.service.tool.impl.YoutubeVideoDownloadService;
import org.creator.autovideocreator.service.work.ScheduledVideoUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduledVideoUploadServiceImpl implements ScheduledVideoUploadService {
    private final ProjectService projectService;
    private final VideoMontageService videoMontageService;
    private final VideoDownloadService videoDownloadService;
    private final InternalVideoService internalVideoService;
    private final VideoUploadService videoUploadService;
    @Value("${video.post.path}")
    private String POST_DIR_PATH;

    private ProjectDto projectDto;

    @Override
    @Scheduled(cron = "0 * * * * *")
    public void startPosting(Long projectId) {
        if (projectDto == null) {
            initializeProjectEntity();
        }

        if (projectDto.getInternalVideos().stream()
                .filter(internalVideo -> internalVideo.getStatus().equals(InternalVideoStatus.TO_POST.toString()))
                .findFirst()
                .isEmpty()
        ) {
            montageSomeVideos();
        }

        LocalTime now = LocalTime.now(ZoneId.of(projectDto.getTimezoneId()));
        if (projectDto.getUploadTimes().contains(now)) {
            uploadVideo();
        }

        Optional<UploadTime> time = projectDto.getUploadTimes().stream()
                .filter(uploadTime -> uploadTime.getTimeToUpload().equals(now))
                .findFirst();

        if (time.isPresent()) {
            uploadVideo();
        }
    }

    private void initializeProjectEntity() {
        projectDto = projectService.getById(projectDto.getId());
    }

    private void uploadVideo() {
        InternalVideo internalVideo = null;
        for (int i = 0; i < projectDto.getInternalVideos().size(); i++) {
            if (projectDto.getInternalVideos().get(i).getStatus().equals(InternalVideoStatus.TO_POST.toString())) {
                projectDto.getInternalVideos().get(i).setStatus(InternalVideoStatus.POSTED.toString());
                internalVideo = projectDto.getInternalVideos().get(i);
                break;
            }
        }
        if (internalVideo == null) {
            throw new NoVideosException("There is no video to post for project " + projectDto.getId());
        }
        initializeProjectEntity();

        File videoToPost =
                new File(POST_DIR_PATH
                        + internalVideo.getId()
                        + YoutubeVideoDownloadService.MP4_EXTENSION_FORMAT);

        for (Account account : projectDto.getAccounts()) {
            videoUploadService.uploadVideo(
                    account.getSecurityToken(),
                    videoToPost,
                    internalVideo.getId().toString(),
                    "DESC",
                    List.of("TAG"));
        }
    }

    private void montageSomeVideos() {
        Optional<YoutubeVideo> youtubeVideo = projectDto.getYoutubeVideos().stream()
                .filter(video -> video.getStatus().equals(YoutubeVideoStatus.TO_CROP.toString()))
                .findFirst();
        if (youtubeVideo.isEmpty()) {
            throw new NoVideosException("There is no youtube video to crop and post in project "
                    + projectDto.getId());
        }
        File downloadedVideo = videoDownloadService.download(
                YoutubeVideoDownloadService.YOUTUBE_VIDEO_BASIC_URL + youtubeVideo.get().getVideoId(),
                youtubeVideo.get().getId().toString());
        List<File> readyToPostVideos = videoMontageService.returnReadyToPostVideosByDuration(downloadedVideo,
                projectDto.getVideosDuration(), youtubeVideo.get().getVideoId());
        readyToPostVideos.forEach(video -> {
            InternalVideo internalVideo = internalVideoService.createAndAddInternalVideo(projectDto.getId());
            video.renameTo(new File(POST_DIR_PATH + internalVideo.getId().toString() + YoutubeVideoDownloadService.MP4_EXTENSION_FORMAT));
        });
        initializeProjectEntity();
    }
}
