package org.creator.autovideocreator.repository;

import org.creator.autovideocreator.model.YoutubeVideo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YoutubeVideoRepository extends JpaRepository<YoutubeVideo, Long> {
}
