package org.creator.autovideocreator.service.tool.impl;

import lombok.RequiredArgsConstructor;
import org.creator.autovideocreator.exception.ExternalProgramExecutionException;
import org.creator.autovideocreator.instrument.ProcessExecutor;
import org.creator.autovideocreator.service.tool.VideoDownloadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class YoutubeVideoDownloadService implements VideoDownloadService {
    public static final String YOUTUBE_VIDEO_BASIC_URL = "https://www.youtube.com/watch?v=";
    @Value("${yt_dlp.path}")
    private String YT_DLP_PATH;
    @Value("${video.source.path}")
    private String OUTPUT_DIRECTORY_PATH;
    private static final String FILE_FORMAT = "%(title)s.%(ext)s";
    private static final String EXTENSION_FORMAT = ".%(ext)s";
    public static final String MP4_EXTENSION_FORMAT = ".mp4";

    private final ProcessExecutor processExecutor;

    @Override
    public File download(String url, String name) {
        List<String> command = new ArrayList<>();
        String outputPath = OUTPUT_DIRECTORY_PATH + name + EXTENSION_FORMAT;
        command.add(YT_DLP_PATH);
        command.add("-S");
        command.add("\"height:1080\"");
        command.add("-f");
        command.add("\"bv*+ba\"");
        command.add("--merge-output-format");
        command.add("mp4");
        command.add("-o");
        command.add("\"" + outputPath + "\"");
        command.add("\"" + url + "\"");

        try {
            processExecutor.execute(command);
        } catch (Exception e) {
            new ExternalProgramExecutionException("Can`t downlaod video using yt-dlp.exe", e);
        }

        return new File(OUTPUT_DIRECTORY_PATH + name + MP4_EXTENSION_FORMAT);
    }

}
