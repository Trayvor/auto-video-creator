package org.creator.autovideocreator.repository;

import org.creator.autovideocreator.model.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @EntityGraph(attributePaths = {"tags", "uploadTimes"})
    List<Project> findByUserTelegramId(String telegramId);

    @EntityGraph(attributePaths = {"accounts"})
    Optional<Project> findByNameAndUserTelegramId(String name, String telegramId);

    @EntityGraph(attributePaths = {"accounts"})
    Optional<Project> findById(Long id);
}
