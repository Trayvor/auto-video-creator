package org.creator.autovideocreator.controller;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.youtube.model.SearchResult;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.creator.autovideocreator.configuration.YoutubeAuth;
import org.creator.autovideocreator.dto.impl.CreateAccountDto;
import org.creator.autovideocreator.dto.impl.CreateProjectDto;
import org.creator.autovideocreator.dto.impl.ProjectDto;
import org.creator.autovideocreator.model.constant.AccountType;
import org.creator.autovideocreator.service.database.AccountService;
import org.creator.autovideocreator.service.database.ProjectService;
import org.creator.autovideocreator.service.database.UserService;
import org.creator.autovideocreator.service.database.YoutubeVideoService;
import org.creator.autovideocreator.service.tool.VideoSearchService;
import org.creator.autovideocreator.service.tool.impl.YoutubeVideoSearchService;
import org.creator.autovideocreator.service.work.ScheduledVideoUploadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/project")
public class ProjectController {
    private final UserService userService;
    private final AccountService accountService;
    private final YoutubeVideoSearchService videoSearchService;
    private final YoutubeVideoService youtubeVideoService;
    private final ScheduledVideoUploadService scheduledVideoUploadService;
    private final ProjectService projectService;

    @PostMapping("/create")
    public ProjectDto createProject(@RequestBody CreateProjectDto createProjectDto,
                                    @RequestParam("user_id") Long userId) {
        return projectService.createAndAddProject(userId, createProjectDto);
    }

    @PostMapping("/addAccount")
    public String addAccount(@RequestParam("project_id") Long projectId) {
        String authorizationUrl =
                YoutubeAuth.getAuthorizationUrl(List.of(YoutubeAuth.UPLOAD_VIDEO_SCOPE), projectId);
        return "Use that URL to authorize in your Youtube account " + authorizationUrl;
    }

    @GetMapping("/oauth2callback")
    public String oauth2callback(@RequestParam("code") String code,
                                 @RequestParam("state") String projectId) {
        try{
            Credential credential = YoutubeAuth.exchangeCodeForTokens(code);
            accountService.createAndAddAccount(Long.valueOf(projectId), new CreateAccountDto(
                    credential.getRefreshToken(), AccountType.YOUTUBE.toString()
            ));
            return "You have been authorized successfully";
        } catch (Exception e) {
            return "Error during token exchange: " + e.getMessage();
        }
    }

    @PostMapping("/searchQuery")
    public ProjectDto findByQuery(@RequestParam("query") String query,
                                     @RequestParam("videos_number") int numberOfVideos,
                                     @RequestParam("project_id") Long projectId) {
        List<SearchResult> videos = videoSearchService.findByName(query, numberOfVideos);
        videos.forEach(
                video -> youtubeVideoService.createAndAddYoutubeVideo(projectId,
                        video.getId().getVideoId())
        );
        return projectService.getById(projectId);
    }

    @PostMapping("/addUploadTimes")
    public ProjectDto addUploadTime(@RequestParam("uploadTime") List<LocalTime> uploadTimes,
                                    @RequestParam("timezone_id") String timezoneId,
                                    @RequestParam("project_id") Long projectId) {
        return projectService.addUploadTime(projectId, uploadTimes, TimeZone.getTimeZone(timezoneId));
    }

    @PostMapping("/startPosting")
    public String startPosting(@RequestParam("projectId") Long projectId) {
        scheduledVideoUploadService.startPosting(projectId);
        return "SCHEDULED POST SERVICE HAS STARTED";
    }
}
