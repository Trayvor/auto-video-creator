package org.creator.autovideocreator.repository;

import org.creator.autovideocreator.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByProjectNameAndProjectUserTelegramId(String projectName, String telegramId);

    List<Account> findByProjectId(Long projectId);
}
