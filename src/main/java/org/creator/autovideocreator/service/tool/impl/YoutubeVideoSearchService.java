package org.creator.autovideocreator.service.tool.impl;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.creator.autovideocreator.configuration.YoutubeApiKey;
import org.creator.autovideocreator.configuration.YoutubeAuth;
import org.creator.autovideocreator.exception.SearchingException;
import org.creator.autovideocreator.service.tool.VideoSearchService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class YoutubeVideoSearchService implements VideoSearchService<SearchResult> {
    private static final String APP_NAME = "auto-video-creator";
    private static final String SEARCH_TYPE = "video";
    private static final String FIELDS =
            "items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)";
    @Override
    public List<SearchResult> findByName(String name, long numberOfVideos) {
        YouTube youtube = new YouTube.Builder(YoutubeAuth.HTTP_TRANSPORT, YoutubeAuth.JSON_FACTORY,
                new HttpRequestInitializer() {
                    @Override
                    public void initialize(HttpRequest httpRequest) throws IOException {
                    }
                }).setApplicationName(APP_NAME).build();
        YouTube.Search.List search = null;
        try {
            search = youtube.search().list(Collections.singletonList("id,snippet"));
            String apiKey = YoutubeApiKey.getApiKey();

            search.setKey(apiKey);
            search.setQ(name);
            search.setType(Collections.singletonList(SEARCH_TYPE));
            search.setFields(FIELDS);
            search.setMaxResults(numberOfVideos);

            SearchListResponse searchResponse = search.execute();
            return searchResponse.getItems();
        } catch (IOException e) {
            throw new SearchingException("Error during searching for video with name: " + name +
                    " on youtube", e);
        }
    }

    @Override
    public SearchResult findByUrl(String url) {
        YouTube youtube = new YouTube.Builder(YoutubeAuth.HTTP_TRANSPORT, YoutubeAuth.JSON_FACTORY,
                new HttpRequestInitializer() {
                    @Override
                    public void initialize(HttpRequest httpRequest) throws IOException {
                    }
                }).setApplicationName(APP_NAME).build();
        YouTube.Search.List search = null;
        try {
            search = youtube.search().list(Collections.singletonList("id,snippet"));
            String apiKey = YoutubeApiKey.getApiKey();

            search.setKey(apiKey);
            search.setQ(url);
            search.setType(Collections.singletonList(SEARCH_TYPE));
            search.setFields(FIELDS);

            SearchListResponse searchResponse = search.execute();
            return searchResponse.getItems().get(0);
        } catch (IOException e) {
            throw new SearchingException("Error during searching for video with url: " + url +
                    " on youtube", e);
        }
    }
}
