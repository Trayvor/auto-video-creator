package org.creator.autovideocreator.repository;

import org.creator.autovideocreator.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
