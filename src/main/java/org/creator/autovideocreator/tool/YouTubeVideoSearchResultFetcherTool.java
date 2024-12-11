package org.creator.autovideocreator.tool;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import lombok.Getter;

import java.io.IOException;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class YouTubeVideoSearchResultFetcherTool {

    public static List<VideoInfo> getVideoDetails(List<SearchResult> resultList,
                                                  String refreshToken) {
        List<VideoInfo> videoDetails = new ArrayList<>();
        YouTube youtubeService = YoutubeAuthTool.createYouTubeInstance(refreshToken);

        List<String> videoIds = new ArrayList<>();
        for (SearchResult result : resultList) {
            if ("youtube#video".equals(result.getId().getKind())) {
                videoIds.add(result.getId().getVideoId());
            }
        }

        if (!videoIds.isEmpty()) {
            try {
                YouTube.Videos.List videoRequest = youtubeService.videos()
                        .list(List.of("snippet", "contentDetails"))
                        .setId(videoIds);

                VideoListResponse response = videoRequest.execute();
                List<Video> videoList = response.getItems();

                for (Video video : videoList) {
                    String durationISO = video.getContentDetails().getDuration();
                    String title = video.getSnippet().getTitle();
                    String videoId = video.getId();

                    long durationInSeconds = convertISO8601DurationToSeconds(durationISO);

                    videoDetails.add(new VideoInfo(title, durationInSeconds, videoId));
                }
            } catch (GoogleJsonResponseException e) {
                System.err.println("YouTube API Error: " + e.getDetails().getMessage());
            } catch (IOException e) {
                System.err.println("I/O Error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected Error: " + e.getMessage());
            }
        }

        return videoDetails;
    }

    private static long convertISO8601DurationToSeconds(String durationISO) {
        try {
            Duration duration = Duration.parse(durationISO);
            return duration.getSeconds();
        } catch (DateTimeParseException e) {
            System.err.println("Failed to parse duration: " + durationISO);
            return 0;
        }
    }

    public record VideoInfo(String title, long durationInSeconds, String videoId) {

        @Override
        public String toString() {
            return "Title: " + title
                    + ", Duration: " + durationInSeconds + " seconds"
                    + ", VideoId: " + videoId;
        }
    }
}
