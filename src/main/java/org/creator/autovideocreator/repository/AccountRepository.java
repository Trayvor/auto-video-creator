package org.creator.autovideocreator.repository;

import org.creator.autovideocreator.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
