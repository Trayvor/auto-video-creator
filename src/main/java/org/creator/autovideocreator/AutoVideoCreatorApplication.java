package org.creator.autovideocreator;

import com.google.api.services.youtube.model.SearchResult;
import lombok.RequiredArgsConstructor;
import org.creator.autovideocreator.mapper.ProjectMapper;
import org.creator.autovideocreator.mapper.UserMapper;
import org.creator.autovideocreator.model.InternalVideo;
import org.creator.autovideocreator.model.YoutubeVideo;
import org.creator.autovideocreator.model.constant.AccountType;
import org.creator.autovideocreator.service.database.InternalVideoService;
import org.creator.autovideocreator.service.database.ProjectService;
import org.creator.autovideocreator.service.database.UserService;
import org.creator.autovideocreator.service.database.YoutubeVideoService;
import org.creator.autovideocreator.service.tool.VideoSearchService;
import org.creator.autovideocreator.service.tool.VideoDownloadService;
import org.creator.autovideocreator.service.tool.VideoMontageService;
import org.creator.autovideocreator.service.tool.VideoUploadService;
import org.creator.autovideocreator.service.tool.impl.YoutubeVideoDownloadService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.List;


@SpringBootApplication
@RequiredArgsConstructor
public class AutoVideoCreatorApplication implements CommandLineRunner {
    private final VideoDownloadService youtubeVideoDownloadService;
    private final VideoSearchService<SearchResult> videoSearchService;
    private final VideoMontageService videoMontageService;
    private final VideoUploadService uploadService;
    private final UserService userService;
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;
    private final UserMapper userMapper;
    private final YoutubeVideoService youtubeVideoService;
    private final InternalVideoService internalVideoService;


    public static void main(String[] args) {
        SpringApplication.run(AutoVideoCreatorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        UserDto user = userService.save(new CreateUserDto("telegram id 1"));
//        ProjectDto project = userService.createAndAddProject(user.getId(), new CreateProjectDto("project 1"));
//        Credential basicCredentials = YoutubeAuth.authorize(List.of(YoutubeAuth.UPLOAD_VIDEO_SCOPE));
//        projectService.createAndAddAccount(project.getId(),
//                new CreateAccountDto(
//                        basicCredentials.getRefreshToken(),
//                        AccountType.YOUTUBE.toString()
//                )
//        );
//
//        List<String> funnyKittens = videoSearchService.findByName("Funny Kittens")
//                .stream()
//                .map(resource -> resource.getId().getVideoId())
//                .toList();
//
//        YoutubeVideo youtubeVideo = youtubeVideoService.createAndAddYoutubeVideo(project.getId(),
//                funnyKittens.getFirst());
//
//        File downloadedVideo = youtubeVideoDownloadService.download(
//                YoutubeVideoDownloadService.YOUTUBE_VIDEO_BASIC_URL + youtubeVideo.getVideoId(),
//                youtubeVideo.getId().toString()
//        );
//
//        InternalVideo internalVideo = internalVideoService.createAndAddInternalVideo(project.getId());
//
//        File firstVideo = videoMontageService.returnReadyToPostVideo(downloadedVideo, 0,
//                30, String.valueOf(internalVideo.getId()));
//

    }
}
