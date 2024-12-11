package org.creator.autovideocreator.mapper;

import org.creator.autovideocreator.configuration.MapperConfig;
import org.creator.autovideocreator.dto.account.ResponseAccountDto;
import org.creator.autovideocreator.model.Account;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfig.class)
public interface AccountMapper {
    ResponseAccountDto toDto(Account account);

    List<ResponseAccountDto> toDto(List<Account> accounts);
}
