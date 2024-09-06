package org.creator.autovideocreator.service.tool.impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.google.common.collect.Lists;
import org.creator.autovideocreator.configuration.YoutubeAuth;
import org.creator.autovideocreator.service.tool.VideoUploadService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

@Service
public class YoutubeVideoUploadService implements VideoUploadService {
    private static final String VIDEO_FILE_FORMAT = "video/*";
    private static final String APP_NAME = "auto-video-creator";
    private static final String PRIVACY_STATUS = "public";

    // filename sample_video.mp4 format
    @Override
    public void uploadVideo(String refreshToken, File file, String title, String description,
                            List<String> tags) {
        try {
            Credential credential = YoutubeAuth.getCredentialByRefreshToken(refreshToken);
            YouTube youtube = new YouTube.Builder(YoutubeAuth.HTTP_TRANSPORT,
                    YoutubeAuth.JSON_FACTORY, credential)
                    .setApplicationName(APP_NAME).build();
            Video video = new Video();

            VideoStatus status = new VideoStatus();
            status.setPrivacyStatus(PRIVACY_STATUS);
            video.setStatus(status);

            VideoSnippet snippet = new VideoSnippet();
            snippet.setTitle(title);
            snippet.setDescription(description);
            snippet.setTags(tags);
            video.setSnippet(snippet);

            InputStreamContent inputStreamContent = new InputStreamContent(VIDEO_FILE_FORMAT,
                    new FileInputStream(file)
            );

            YouTube.Videos.Insert insert = youtube.videos().insert(Collections.singletonList("snippet,statistics,status"),
                    video, inputStreamContent);

            MediaHttpUploader uploader = insert.getMediaHttpUploader();
            uploader.setDirectUploadEnabled(false);
            Video returnedVideo = insert.execute();

            System.out.println("\n================== Returned Video ==================\n");
            System.out.println("  - Id: " + returnedVideo.getId());
            System.out.println("  - Title: " + returnedVideo.getSnippet().getTitle());
            System.out.println("  - Tags: " + returnedVideo.getSnippet().getTags());
            System.out.println("  - Privacy Status: " + returnedVideo.getStatus().getPrivacyStatus());
            System.out.println("  - Video Count: " + returnedVideo.getStatistics().getViewCount());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
