package org.creator.autovideocreator.dto.impl;

import org.creator.autovideocreator.dto.UpdateDto;
import org.creator.autovideocreator.model.Project;

import java.util.List;

//USELESS
public record UpdateUserDto(List<Project> projectList) implements UpdateDto {
}
