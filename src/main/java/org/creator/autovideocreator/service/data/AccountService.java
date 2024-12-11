package org.creator.autovideocreator.service.data;

import org.creator.autovideocreator.dto.account.ResponseAccountDto;
import org.creator.autovideocreator.model.Account.AccountType;

import java.util.List;

public interface AccountService {
    List<ResponseAccountDto> addAccount(Long projectId, String token, AccountType accountType);
}
