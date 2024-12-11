package org.creator.autovideocreator.controller;

import lombok.RequiredArgsConstructor;
import org.creator.autovideocreator.dto.account.RequestAccountDto;
import org.creator.autovideocreator.service.data.AccountService;
import org.creator.autovideocreator.service.data.ProjectService;
import org.creator.autovideocreator.tool.YoutubeAuthTool;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;
    private final ProjectService projectService;

    @PostMapping("/add-youtube-account")
    public String addYoutubeAccount(@RequestBody RequestAccountDto requestAccountDto) {
        Long projectId = projectService.findByTelegramId(requestAccountDto.telegramId())
                .stream()
                .filter(project -> project.name().equals(requestAccountDto.projectName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Project with telegram id "
                        + requestAccountDto.telegramId() + " and project name "
                        + requestAccountDto.projectName() + " not found"))
                .id();

        String authorizationUrl =
                YoutubeAuthTool.getAuthorizationUrl(List.of(YoutubeAuthTool.UPLOAD_VIDEO_SCOPE,
                                YoutubeAuthTool.FORCE_SSL_SCOPE, YoutubeAuthTool.READONLY_VIDEO_SCOPE),
                        projectId);
        return "Use that URL to authorize in your Youtube account " + authorizationUrl;
    }
}
