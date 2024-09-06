package org.creator.autovideocreator.service.database.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.creator.autovideocreator.dto.impl.CreateAccountDto;
import org.creator.autovideocreator.dto.impl.ProjectDto;
import org.creator.autovideocreator.mapper.AccountMapper;
import org.creator.autovideocreator.model.Account;
import org.creator.autovideocreator.model.Project;
import org.creator.autovideocreator.repository.ProjectRepository;
import org.creator.autovideocreator.service.database.AccountService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final ProjectRepository projectRepository;
    private final AccountMapper accountMapper;
    @Override
    @Transactional
    public Account createAndAddAccount(Long projectId, CreateAccountDto createAccountDto) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Can`t find project with id "
                        + projectId)
        );
        Account account = accountMapper.toModel(createAccountDto);
        account.setProject(project);
        project.getAccounts().add(account);
        projectRepository.save(project);
        return account;
    }
}
