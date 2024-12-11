package org.creator.autovideocreator.service.data;

import org.creator.autovideocreator.dto.project.CreateProjectDto;
import org.creator.autovideocreator.dto.project.ResponseProjectDto;

import java.util.List;

public interface ProjectService {
    ResponseProjectDto save(CreateProjectDto createProjectDto);

    List<ResponseProjectDto> findByTelegramId(String telegramId);
}
