package org.creator.autovideocreator.service.tool;

import java.util.List;

public interface VideoSearchService<T> {
    List<T> findByName(String name, long numberOfVideos);
    T findByUrl(String url);
}
