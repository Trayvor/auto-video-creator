package org.creator.autovideocreator.dto.impl;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.creator.autovideocreator.dto.UsualDto;
import org.creator.autovideocreator.model.Account;
import org.creator.autovideocreator.model.InternalVideo;
import org.creator.autovideocreator.model.UploadTime;
import org.creator.autovideocreator.model.User;
import org.creator.autovideocreator.model.YoutubeVideo;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectDto implements UsualDto {
    @NotBlank
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private List<YoutubeVideo> youtubeVideos = new ArrayList<>();
    @NotBlank
    private List<InternalVideo> internalVideos = new ArrayList<>();
    @NotBlank
    private User user;
    @NotBlank
    private List<UploadTime> uploadTimes = new ArrayList<>();
    @NotBlank
    private List<Account> accounts = new ArrayList<>();
    @NotBlank
    private int videosDuration;
    @NotBlank
    private String timezoneId;
}
