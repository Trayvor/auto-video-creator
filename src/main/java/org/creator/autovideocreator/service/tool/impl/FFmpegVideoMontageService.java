package org.creator.autovideocreator.service.tool.impl;

import lombok.RequiredArgsConstructor;
import org.bytedeco.ffmpeg.avformat.AVFormatContext;
import org.bytedeco.ffmpeg.avformat.AVStream;
import org.bytedeco.ffmpeg.global.avformat;
import org.bytedeco.ffmpeg.global.avutil;
import org.creator.autovideocreator.exception.ExternalProgramExecutionException;
import org.creator.autovideocreator.exception.UnableToOpenVideoException;
import org.creator.autovideocreator.instrument.ProcessExecutor;
import org.creator.autovideocreator.service.tool.VideoMontageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FFmpegVideoMontageService implements VideoMontageService {
    @Value("${ffmpeg.path}")
    private  String FFMPEG_PATH;
    @Value("${video.temp.path}")
    private String TEMPORARY_DIR_PATH;
    @Value("${video.post.path}")
    private String POST_DIR_PATH;
    private final ProcessExecutor processExecutor;

    @Override
    public File cropByTime(File inputVideo, File outputVideo, int startTime, int endTime) {
        List<String> command = new ArrayList<>();
        command.add(FFMPEG_PATH); // Path to ffmpeg
        command.add("-ss"); // Start time
        command.add(String.valueOf(startTime));
        command.add("-t"); // Duration
        command.add(String.valueOf(endTime - startTime));
        command.add("-y");
        command.add("-i"); // Input file
        command.add(inputVideo.getAbsolutePath());
        command.add(outputVideo.getAbsolutePath()); // Output file
        try {
            processExecutor.execute(command);
        } catch (IOException | InterruptedException e) {
            throw new ExternalProgramExecutionException("Can`t crop video " + inputVideo
            + " to duration from " + startTime + " to " + endTime + " into file " + outputVideo, e);
        }
        return outputVideo;
    }

    @Override
    public File cropByDuration(File inputVideo, File outputVideo, int startTime, int duration) {
        List<String> command = new ArrayList<>();
        command.add(FFMPEG_PATH); // Path to ffmpeg
        command.add("-ss"); // Start time
        command.add(String.valueOf(startTime));
        command.add("-t"); // Duration
        command.add(String.valueOf(duration));
        command.add("-y");
        command.add("-i"); // Input file
        command.add(inputVideo.getAbsolutePath());
        command.add(outputVideo.getAbsolutePath()); // Output file
        try {
            processExecutor.execute(command);
        } catch (IOException | InterruptedException e) {
            throw new ExternalProgramExecutionException("Can`t crop video " + inputVideo
                    + " to duration from " + startTime + " duration: " + duration +" into file "
                    + outputVideo, e);
        }
        return outputVideo;
    }

    @Override
    public File horizontalToVertical(File inputVideo, File outputVideo) {
        List<String> command = new ArrayList<>();
        command.add(FFMPEG_PATH); // Path to ffmpeg
        command.add("-i"); // Input file
        command.add(inputVideo.getAbsolutePath());
        command.add("-aspect"); // Aspect ratio
        command.add("9:16"); // Desired aspect ratio
        command.add(outputVideo.getAbsolutePath()); // Output file

        try{
            processExecutor.execute(command);
        } catch (IOException | InterruptedException e) {
            throw new ExternalProgramExecutionException("Can't make video: " + inputVideo +
                    " horizontal in video: " + outputVideo +
                    " using ffmpeg: " + FFMPEG_PATH, e);
        }
        return outputVideo;
    }

    @Override
    public File blurVideo(File inputVideo, File outputVideo) {
        List<String> command = new ArrayList<>();
        command.add(FFMPEG_PATH); // Path to ffmpeg
        command.add("-y");
        command.add("-i"); // Input file
        command.add(inputVideo.getAbsolutePath());
        command.add("-vf"); // Aspect ratio
        command.add("\"gblur=sigma=20\""); // Blur
        command.add(outputVideo.getAbsolutePath()); // Output file

        try {
            processExecutor.execute(command);
        }
        catch (IOException | InterruptedException e) {
            throw new ExternalProgramExecutionException("Can't make video: " + inputVideo +
                    " blurred in video: " + outputVideo +
                    " using ffmpeg: " + FFMPEG_PATH, e);
        }
        return outputVideo;
    }

    @Override
    public File scaleVideo(File inputVideo, File outputVideo) {
        List<String> command = new ArrayList<>();
        command.add(FFMPEG_PATH);
        command.add("-i");
        command.add(inputVideo.getAbsolutePath());
        command.add("-vf");
        command.add("\"scale=trunc(iw/2):trunc(ih/2)\"");
        command.add(outputVideo.getAbsolutePath());

        try {
            processExecutor.execute(command);
        } catch (IOException | InterruptedException e) {
            throw new ExternalProgramExecutionException("Can`t scale video " + inputVideo +
                    " to video " + outputVideo, e);
        }
        return outputVideo;
    }

    @Override
    public File overlayVideosCenter(File backgroundVideo, File overlayVideo, File outputVideo) {
        List<String> command = new ArrayList<>();
        command.add(FFMPEG_PATH); // Path to ffmpeg
        command.add("-y");
        command.add("-i"); // Input file
        command.add(backgroundVideo.getAbsolutePath());
        command.add("-i"); // Input file
        command.add(overlayVideo.getAbsolutePath());
        command.add("-filter_complex");
        command.add("\"[1:v]scale=(1280*1.5):(240*1.5)[ov];[0][ov]overlay=(W-w)/2:(H-h)/2\""); //
        // Blur
        command.add(outputVideo.getAbsolutePath()); // Output file

        try {
            processExecutor.execute(command);
        }
        catch (IOException | InterruptedException e) {
            throw new ExternalProgramExecutionException("Can't put video: " + overlayVideo +
                    " over video " + backgroundVideo +
                    " in video " + outputVideo +
                    " using ffmpeg: " + FFMPEG_PATH, e);
        }
        return outputVideo;
    }

    @Override
    public File overlayAudioOnVideo(File inputVideo, File inputAudio, File outputVideo) {
        List<String> command = new ArrayList<>();
        command.add(FFMPEG_PATH); // Path to ffmpeg
        command.add("-y");
        command.add("-i"); // Input file
        command.add(inputVideo.getAbsolutePath());
        command.add("-i"); // Input file
        command.add(inputAudio.getAbsolutePath());
        command.add("-c:v");
        command.add("copy");
        command.add("-c:a");
        command.add("aac");
        command.add("-map");
        command.add("0:v:0");
        command.add("-map");
        command.add("1:a:0");
        command.add("-shortest");
        command.add(outputVideo.getAbsolutePath()); // Output file

        try {
            processExecutor.execute(command);
        }
        catch (IOException | InterruptedException e) {
            throw new ExternalProgramExecutionException("Can't put audio: " + inputAudio +
                    " over video " + inputVideo +
                    " in video " + outputVideo +
                    " using ffmpeg: " + FFMPEG_PATH, e);
        }
        return outputVideo;
    }

    @Override
    public File returnReadyToPostVideo(File inputVideo, int startTime,
                                       int endTime, String outputFileName) {
        List<File> tempFiles = new ArrayList<>();
        File smallerVideo = new File(TEMPORARY_DIR_PATH + outputFileName + "_smaller");
        File blurredVideo = new File(TEMPORARY_DIR_PATH + outputFileName + "_blurred");
        File verticalVideo = new File(TEMPORARY_DIR_PATH + outputFileName + "_vertical");
        File scaledVideo = new File(TEMPORARY_DIR_PATH + outputFileName + "_scaled");
        File fullVideo = new File(POST_DIR_PATH + outputFileName);

        tempFiles.add(cropByTime(inputVideo, smallerVideo, startTime, endTime));
        tempFiles.add(blurVideo(smallerVideo, blurredVideo));
        tempFiles.add(horizontalToVertical(blurredVideo, verticalVideo));
        tempFiles.add(scaleVideo(smallerVideo, scaledVideo));
        overlayVideosCenter(verticalVideo, scaledVideo, fullVideo);

        tempFiles.forEach(File::delete);

        return fullVideo;
    }

    @Override
    public File returnReadyToPostVideo(File inputVideo, File inputAudio,
                                       int startTime, int endTime, String outputFileName) {
        List<File> tempFiles = new ArrayList<>();
        File smallerVideo = new File(TEMPORARY_DIR_PATH + outputFileName + "_smaller.mp4");
        File blurredVideo = new File(TEMPORARY_DIR_PATH + outputFileName + "_blurred.mp4");
        File verticalVideo = new File(TEMPORARY_DIR_PATH + outputFileName + "_vertical.mp4");
        File scaledVideo = new File(TEMPORARY_DIR_PATH + outputFileName + "_scaled.mp4");
        File overlayedVideo = new File(TEMPORARY_DIR_PATH + outputFileName + "_overlayed.mp4");
        File fullVideo = new File(POST_DIR_PATH + outputFileName);

        tempFiles.add(cropByTime(inputVideo, smallerVideo, startTime, endTime));
        tempFiles.add(blurVideo(smallerVideo, blurredVideo));
        tempFiles.add(horizontalToVertical(blurredVideo, verticalVideo));
        tempFiles.add(scaleVideo(smallerVideo, scaledVideo));
        tempFiles.add(overlayVideosCenter(verticalVideo, scaledVideo, overlayedVideo));
        overlayAudioOnVideo(overlayedVideo, inputAudio, fullVideo);

        tempFiles.forEach(File::delete);

        return fullVideo;
    }

    @Override
    public List<File> returnReadyToPostVideosByDuration(File inputVideo, int duration, String basicOutputName) {
        List<File> videos = new ArrayList<>();
        int videoDuration = getVideoDuration(inputVideo);
        int numberOfVideos = videoDuration/duration;
        for (int i = 0; i < numberOfVideos; i++) {
            File readyToPostVideo = returnReadyToPostVideo(inputVideo, i * duration,
                    (i + 1) * duration, basicOutputName + i * duration);
            videos.add(readyToPostVideo);
        }
        return videos;
    }

    private int getVideoDuration(File video) {
        AVFormatContext formatContext = avformat.avformat_alloc_context();
        if (avformat.avformat_open_input(formatContext, video.getAbsolutePath(),
                null, null) != 0) {
            throw new UnableToOpenVideoException("Can`t open file " + video);
        }

        if (avformat.avformat_find_stream_info(formatContext, (org.bytedeco.ffmpeg.avutil.AVDictionary) null) < 0) {
            System.err.println("Could not find stream information");
        }

        AVStream videoStream = formatContext.streams(0);
        long videoDuration = videoStream.duration();

        double durationInSeconds = videoDuration / (double) avutil.AV_TIME_BASE;
        avformat.avformat_close_input(formatContext);
        return (int) durationInSeconds;
    }
}
