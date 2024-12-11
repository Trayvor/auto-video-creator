package org.creator.autovideocreator.dto.account;

import org.creator.autovideocreator.model.Account;

public record ResponseAccountDto(Long id, String securityToken, Account.AccountType accountType) {
}
