package org.creator.autovideocreator.service.upload.impl;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import org.creator.autovideocreator.model.Tag;
import org.creator.autovideocreator.service.upload.VideoUploadService;
import org.creator.autovideocreator.tool.YoutubeAuthTool;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.creator.autovideocreator.tool.YoutubeAuthTool.APP_NAME;

@Service
public class YoutubeVideoUploadServiceImpl implements VideoUploadService {
    private static final String VIDEO_FILE_FORMAT = "video/*";
    private static final String PRIVACY_STATUS = "public";

    // filename sample_video.mp4 format
    @Override
    public void uploadVideo(String refreshToken, File file, String title, String description,
                            List<Tag> tags) {
        List<String> tagList = tags.stream()
                .map(Tag::getName)
                .toList();
        try {
            YouTube youtube = YoutubeAuthTool.createYouTubeInstance(refreshToken);
            Video video = new Video();

            VideoStatus status = new VideoStatus();
            status.setPrivacyStatus(PRIVACY_STATUS);
            video.setStatus(status);

            VideoSnippet snippet = new VideoSnippet();
            snippet.setTitle(title);
            snippet.setDescription(description);
            snippet.setTags(tagList);
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
