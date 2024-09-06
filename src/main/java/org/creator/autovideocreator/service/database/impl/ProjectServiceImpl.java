package org.creator.autovideocreator.service.database.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.creator.autovideocreator.dto.impl.CreateAccountDto;
import org.creator.autovideocreator.dto.impl.CreateProjectDto;
import org.creator.autovideocreator.dto.impl.ProjectDto;
import org.creator.autovideocreator.mapper.AccountMapper;
import org.creator.autovideocreator.mapper.ProjectMapper;
import org.creator.autovideocreator.model.Account;
import org.creator.autovideocreator.model.InternalVideo;
import org.creator.autovideocreator.model.UploadTime;
import org.creator.autovideocreator.model.User;
import org.creator.autovideocreator.model.constant.InternalVideoStatus;
import org.creator.autovideocreator.model.Project;
import org.creator.autovideocreator.model.YoutubeVideo;
import org.creator.autovideocreator.model.constant.YoutubeVideoStatus;
import org.creator.autovideocreator.repository.AccountRepository;
import org.creator.autovideocreator.repository.ProjectRepository;
import org.creator.autovideocreator.repository.UserRepository;
import org.creator.autovideocreator.service.database.ProjectService;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;


@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public ProjectDto getById(Long id) {
        return projectMapper.toDto(projectRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can`t find project with id " + id)));
    }

    @Override
    public List<ProjectDto> findAll() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toDto)
                .toList();
    }

    @Override
    public ProjectDto save(CreateProjectDto entity) {
        return projectMapper.toDto(projectRepository.save(projectMapper.toModel(entity)));
    }

    @Override
    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public ProjectDto setUploadTime(Long projectId, List<UploadTime> uploadTimeList) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Can`t find project with id " + projectId)
        );
        project.getUploadTimes().addAll(uploadTimeList);
        projectRepository.save(project);
        return projectMapper.toDto(project);
    }

    @Override
    public ProjectDto clearUploadTime(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Can`t find project with id " + projectId)
        );
        project.getUploadTimes().clear();
        projectRepository.save(project);
        return projectMapper.toDto(project);
    }

    @Override
    public ProjectDto addUploadTime(Long projectId, List<LocalTime> localTimeList, TimeZone timeZone) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Can`t find project with id " + projectId)
        );
        List<UploadTime> uploadTimeList = new ArrayList<>();
        localTimeList.forEach(
                time -> {
                    UploadTime uploadTime = new UploadTime();
                    uploadTime.setTimeToUpload(time);
                    uploadTime.setProject(project);
                    uploadTimeList.add(uploadTime);
                }
        );
        project.getUploadTimes().addAll(uploadTimeList);
        project.setTimezoneId(timeZone.getID());
        projectRepository.save(project);
        return projectMapper.toDto(project);
    }

    @Override
    @Transactional
    public ProjectDto createAndAddProject(Long userId, CreateProjectDto createProjectDto) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Can`t find user with id "
                        + userId + "to add a project")
        );
        Project project = projectMapper.toModel(createProjectDto);
        project.setUser(user);
        user.getProjects().add(project);
        user = userRepository.save(user);
        return projectMapper.toDto(user.getProjects().stream()
                .filter(p -> p.getName().equals(createProjectDto.name()))
                .findFirst()
                .orElseThrow(
                        () -> new EntityNotFoundException("Can`t find project with name "
                                + createProjectDto.name())
                ));
    }
}
