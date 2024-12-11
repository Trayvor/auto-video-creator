package org.creator.autovideocreator.tool;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import org.creator.autovideocreator.exception.SearchingException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.creator.autovideocreator.tool.YoutubeAuthTool.APP_NAME;

public class YoutubeVideoSearchTool{
    private static final String SEARCH_TYPE = "video";
    private static final String FIELDS =
            "items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)";

    public static List<SearchResult> findByName(String name, long numberOfVideos,
                                                String refreshToken) {
        YouTube youtube = YoutubeAuthTool.createYouTubeInstance(refreshToken);
        YouTube.Search.List search = null;
        try {
            search = youtube.search().list(Collections.singletonList("id,snippet"));
            search.setQ(name);
            search.setType(Collections.singletonList(SEARCH_TYPE));
            search.setFields(FIELDS);
            search.setMaxResults(numberOfVideos);
            search.setVideoDuration("long");


            SearchListResponse searchResponse = search.execute();

            return searchResponse.getItems();
        } catch (IOException e) {
            throw new SearchingException("Error during searching for video with name: " + name +
                    " on youtube", e);
        }
    }

    public static SearchResult findByUrl(String url, String refreshToken) {
        YouTube youtube = YoutubeAuthTool.createYouTubeInstance(refreshToken);
        YouTube.Search.List search = null;
        try {
            search = youtube.search().list(Collections.singletonList("id,snippet"));
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
