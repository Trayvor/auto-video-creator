package org.creator.autovideocreator.service.tool;

import java.io.File;
import java.util.List;

public interface VideoMontageService {
    File cropByTime(File inputVideo, File outputVideo, int startTime, int endTime);
    File cropByDuration(File inputVideo, File outputVideo, int startTime, int duration);
    File horizontalToVertical(File inputVideo, File outputVideo);
    File blurVideo(File inputVideo, File outputVideo);
    File scaleVideo(File inputVideo, File outputVideo);
    File overlayVideosCenter(File backgroundVideo, File overlayVideo, File outputVideo);
    File overlayAudioOnVideo(File inputVideo, File inputAudio, File outputVideo);
    File returnReadyToPostVideo(File inputVideo, int startTime,
                                int endTime, String outputFileName);
    File returnReadyToPostVideo(File inputVideo, File inputAudio,
                                int startTime, int endTime, String outputFileName);
    List<File> returnReadyToPostVideosByDuration(File inputVideo, int duration,
                                                 String basicOutputName);
}
