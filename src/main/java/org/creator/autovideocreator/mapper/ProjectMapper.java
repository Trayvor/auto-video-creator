package org.creator.autovideocreator.mapper;

import org.creator.autovideocreator.configuration.MapperConfig;
import org.creator.autovideocreator.dto.impl.CreateProjectDto;
import org.creator.autovideocreator.dto.impl.ProjectDto;
import org.creator.autovideocreator.model.Project;
import org.mapstruct.Mapper;


@Mapper(config = MapperConfig.class)
public interface ProjectMapper {
    ProjectDto toDto(Project project);
    Project toModel(ProjectDto projectDto);
    Project toModel(CreateProjectDto projectDto);
}
