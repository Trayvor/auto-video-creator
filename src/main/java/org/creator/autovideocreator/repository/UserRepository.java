package org.creator.autovideocreator.repository;

import org.creator.autovideocreator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByTelegramId(String telegramId);
}
