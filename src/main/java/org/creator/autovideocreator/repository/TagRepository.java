package org.creator.autovideocreator.repository;

import org.creator.autovideocreator.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByProjectId(Long projectId);
}
