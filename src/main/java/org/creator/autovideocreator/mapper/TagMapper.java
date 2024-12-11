package org.creator.autovideocreator.mapper;

import org.creator.autovideocreator.configuration.MapperConfig;
import org.creator.autovideocreator.dto.tag.CreateTagDto;
import org.creator.autovideocreator.dto.tag.ResponseTagDto;
import org.creator.autovideocreator.model.Tag;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface TagMapper {
    ResponseTagDto toDto(Tag tag);

    Tag toModel(CreateTagDto createTagDto);
}
