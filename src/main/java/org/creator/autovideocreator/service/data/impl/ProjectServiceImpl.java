package org.creator.autovideocreator.service.data.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.creator.autovideocreator.dto.project.CreateProjectDto;
import org.creator.autovideocreator.dto.project.ResponseProjectDto;
import org.creator.autovideocreator.mapper.ProjectMapper;
import org.creator.autovideocreator.model.Project;
import org.creator.autovideocreator.model.Tag;
import org.creator.autovideocreator.model.UploadTime;
import org.creator.autovideocreator.model.User;
import org.creator.autovideocreator.repository.ProjectRepository;
import org.creator.autovideocreator.repository.UserRepository;
import org.creator.autovideocreator.service.data.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;

    @Override
    public ResponseProjectDto save(CreateProjectDto createProjectDto) {
        User user = userRepository.findByTelegramId(createProjectDto.telegramId())
                .orElseThrow(
                        () -> new EntityNotFoundException("User with telegram id "
                                + createProjectDto.telegramId() + " not found")
                );
        Project project = projectMapper.toModel(createProjectDto);
        project.setUser(user);
        for (Tag tag : project.getTags()) {
            tag.setProject(project);
        }
        for (UploadTime uploadTime : project.getUploadTimes()) {
            uploadTime.setProject(project);
        }

        projectRepository.save(project);
        return projectMapper.toDto(project);
    }

    @Override
    public List<ResponseProjectDto> findByTelegramId(String telegramId) {
        return projectMapper.toDto(projectRepository.findByUserTelegramId(telegramId));
    }
}
