package org.creator.autovideocreator.repository;

import org.creator.autovideocreator.model.InternalVideo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InternalVideoRepository extends JpaRepository<InternalVideo, Long> {
}
