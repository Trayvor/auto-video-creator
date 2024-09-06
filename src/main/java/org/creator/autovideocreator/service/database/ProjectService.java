package org.creator.autovideocreator.service.database;

import org.creator.autovideocreator.dto.impl.CreateAccountDto;
import org.creator.autovideocreator.dto.impl.CreateProjectDto;
import org.creator.autovideocreator.dto.impl.ProjectDto;
import org.creator.autovideocreator.model.Account;
import org.creator.autovideocreator.model.InternalVideo;
import org.creator.autovideocreator.model.UploadTime;
import org.creator.autovideocreator.model.YoutubeVideo;


import java.time.LocalTime;
import java.util.List;
import java.util.TimeZone;

public interface ProjectService {
    ProjectDto getById(Long id);
    List<ProjectDto> findAll();
    ProjectDto save(CreateProjectDto entity);
    void deleteById(Long id);
    ProjectDto setUploadTime(Long projectId, List<UploadTime> uploadTimeList);
    ProjectDto clearUploadTime(Long projectId);
    public ProjectDto addUploadTime(Long projectId, List<LocalTime> uploadTimeList,
                                    TimeZone timeZone);
    ProjectDto createAndAddProject(Long userId, CreateProjectDto createProjectDto);
}
