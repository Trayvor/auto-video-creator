package org.creator.autovideocreator.repository;

import org.creator.autovideocreator.model.YoutubeVideo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface YoutubeVideoRepository extends JpaRepository<YoutubeVideo, Long> {
    List<YoutubeVideo> findByProjectNameAndProjectUserTelegramId(String project_name, String project_user_telegramId);

    Optional<YoutubeVideo> findFirstByProjectId(Long projectId);
}
