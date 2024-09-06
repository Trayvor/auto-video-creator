package org.creator.autovideocreator.service.database.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.creator.autovideocreator.model.InternalVideo;
import org.creator.autovideocreator.model.Project;
import org.creator.autovideocreator.model.constant.InternalVideoStatus;
import org.creator.autovideocreator.repository.InternalVideoRepository;
import org.creator.autovideocreator.repository.ProjectRepository;
import org.creator.autovideocreator.service.database.InternalVideoService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InternalVideoServiceImpl implements InternalVideoService {
    private final InternalVideoRepository internalVideoRepository;
    private final ProjectRepository projectRepository;

    @Override
    public InternalVideo changeStatus(Long id, InternalVideoStatus status) {
        InternalVideo internalVideo = internalVideoRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can`t find internal video with id "
                        + id)
        );
        internalVideo.setStatus(status.toString());
        internalVideoRepository.save(internalVideo);
        return internalVideo;
    }

    @Override
    @Transactional
    public InternalVideo createAndAddInternalVideo(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Can`t find project with id " + projectId)
        );
        InternalVideo internalVideo = new InternalVideo();
        internalVideo.setProject(project);
        internalVideo.setStatus(InternalVideoStatus.TO_POST.toString());
        project.getInternalVideos().add(internalVideo);
        Project savedProject = projectRepository.save(project);
        return savedProject.getInternalVideos().getLast();
    }
}
