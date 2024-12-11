package org.creator.autovideocreator.controller;

import com.google.api.client.auth.oauth2.Credential;
import lombok.RequiredArgsConstructor;
import org.creator.autovideocreator.model.Account;
import org.creator.autovideocreator.service.data.AccountService;
import org.creator.autovideocreator.tool.YoutubeAuthTool;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/callback")
public class CallbackController {
    private final AccountService accountService;

    @GetMapping("/oauth2callback")
    public String oauth2callback(@RequestParam("code") String code,
                                 @RequestParam("state") String projectId) {
        try{
            Credential credential = YoutubeAuthTool.exchangeCodeForTokens(code);
            accountService.addAccount(Long.valueOf(projectId),
                    credential.getRefreshToken(), Account.AccountType.YOUTUBE);

            return "You have been authorized successfully";
        } catch (Exception e) {
            return "Error during token exchange: " + e.getMessage();
        }
    }
}
