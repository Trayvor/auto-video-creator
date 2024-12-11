package org.creator.autovideocreator.mapper;

import org.creator.autovideocreator.configuration.MapperConfig;
import org.creator.autovideocreator.dto.project.CreateProjectDto;
import org.creator.autovideocreator.dto.project.ResponseProjectDto;
import org.creator.autovideocreator.model.Project;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfig.class, uses = {TagMapper.class, UploadTimeMapper.class})
public interface ProjectMapper {
    ResponseProjectDto toDto(Project project);

    List<ResponseProjectDto> toDto(List<Project> projects);

    Project toModel(CreateProjectDto createProjectDto);
}
