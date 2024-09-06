package org.creator.autovideocreator.service.database;

import org.creator.autovideocreator.dto.impl.CreateAccountDto;
import org.creator.autovideocreator.model.Account;

public interface AccountService {
    Account createAndAddAccount(Long projectId, CreateAccountDto createAccountDto);
}
