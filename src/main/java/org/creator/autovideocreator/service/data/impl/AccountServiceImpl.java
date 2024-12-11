package org.creator.autovideocreator.service.data.impl;

import lombok.RequiredArgsConstructor;
import org.creator.autovideocreator.dto.account.ResponseAccountDto;
import org.creator.autovideocreator.mapper.AccountMapper;
import org.creator.autovideocreator.model.Account;
import org.creator.autovideocreator.model.Account.AccountType;
import org.creator.autovideocreator.model.Project;
import org.creator.autovideocreator.repository.AccountRepository;
import org.creator.autovideocreator.repository.ProjectRepository;
import org.creator.autovideocreator.service.data.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ProjectRepository projectRepository;
    private final AccountMapper accountMapper;

    @Override
    public List<ResponseAccountDto> addAccount(Long projectId, String token, AccountType accountType) {
        if (projectRepository.existsById(projectId)) {
            Account account = new Account()
                    .setAccountType(accountType)
                    .setProject(new Project().setId(projectId))
                    .setSecurityToken(token);
            accountRepository.save(account);
        }

        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new RuntimeException("Project with id " + projectId + " not found")
        );
        return accountMapper.toDto(project.getAccounts());
    }
}
