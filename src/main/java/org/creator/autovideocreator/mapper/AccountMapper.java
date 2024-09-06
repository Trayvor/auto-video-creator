package org.creator.autovideocreator.mapper;

import org.creator.autovideocreator.configuration.MapperConfig;
import org.creator.autovideocreator.dto.impl.CreateAccountDto;
import org.creator.autovideocreator.model.Account;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface AccountMapper {
    Account toModel(CreateAccountDto createYoutubeAccountDto);
}
