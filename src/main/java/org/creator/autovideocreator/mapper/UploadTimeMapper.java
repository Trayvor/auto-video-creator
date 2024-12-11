package org.creator.autovideocreator.mapper;

import org.creator.autovideocreator.configuration.MapperConfig;
import org.creator.autovideocreator.dto.upload.time.CreateUploadTimeDto;
import org.creator.autovideocreator.dto.upload.time.ResponseUploadTimeDto;
import org.creator.autovideocreator.model.UploadTime;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UploadTimeMapper {
    UploadTime toModel(CreateUploadTimeDto uploadTimeDto);

    ResponseUploadTimeDto toDto(UploadTime uploadTime);
}
