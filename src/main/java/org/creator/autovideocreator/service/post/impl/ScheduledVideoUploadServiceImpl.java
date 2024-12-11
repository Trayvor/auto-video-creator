package org.creator.autovideocreator.service.post.impl;

import lombok.RequiredArgsConstructor;
import org.creator.autovideocreator.exception.NoVideosException;
import org.creator.autovideocreator.model.*;
import org.creator.autovideocreator.repository.AccountRepository;
import org.creator.autovideocreator.repository.TagRepository;
import org.creator.autovideocreator.repository.UploadTimeRepository;
import org.creator.autovideocreator.repository.YoutubeVideoRepository;
import org.creator.autovideocreator.service.upload.VideoUploadService;
import org.creator.autovideocreator.service.post.ScheduledVideoUploadService;
import org.creator.autovideocreator.tool.ScriptVideoMontageTool;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.File;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduledVideoUploadServiceImpl implements ScheduledVideoUploadService {
    private final UploadTimeRepository uploadTimeRepository;
    private final YoutubeVideoRepository youtubeVideoRepository;
    private final TagRepository tagRepository;
    private final AccountRepository accountRepository;
    private final VideoUploadService videoUploadService;

    @Override
    @Scheduled(cron = "0 * * * * *")
    public void scheduledPosting() {
        ZonedDateTime currentTimeUtc = ZonedDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MINUTES);
        LocalTime currentTime = currentTimeUtc.toLocalTime().truncatedTo(ChronoUnit.MINUTES);


        List<UploadTime> byTimeToUpload = uploadTimeRepository.findByTimeToUpload(currentTime);
        for (UploadTime uploadTime : byTimeToUpload) {
            YoutubeVideo youtubeVideo = youtubeVideoRepository
                    .findFirstByProjectId(uploadTime.getProject().getId())
                    .orElseThrow(() -> new NoVideosException("No video found for project with id "
                            + uploadTime.getProject().getId()));

            String outputVideoName = uploadTime.getProject().getName()
                            + "_" + youtubeVideo.getVideoId()
                            + "_" + youtubeVideo.getStartTime()
                            + "_" + youtubeVideo.getEndTime();
            File video = ScriptVideoMontageTool.createVideo(youtubeVideo.getVideoId(),
                    uploadTime.getProject().getAnotherVideo(), outputVideoName,
                    youtubeVideo.getStartTime(), youtubeVideo.getEndTime(),
                    uploadTime.getProject().getIsOverlay());
            uploadOnAccounts(video, youtubeVideo, uploadTime.getProject().getId());
            if (video.exists()) {
                video.delete();
            }
            youtubeVideoRepository.deleteById(youtubeVideo.getId());
        }
    }

    private void uploadOnAccounts(File video, YoutubeVideo youtubeVideo, Long projectId) {
        List<Account> accountList = accountRepository.findByProjectId(projectId);
        List<Tag> tagList = tagRepository.findByProjectId(projectId);

        for (Account account : accountList) {
            videoUploadService.uploadVideo(account.getSecurityToken(), video,
                    youtubeVideo.getTitle(), youtubeVideo.getTitle(), tagList);
        }
    }
}
