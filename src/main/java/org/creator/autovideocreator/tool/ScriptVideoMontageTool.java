package org.creator.autovideocreator.tool;

import lombok.RequiredArgsConstructor;
import org.creator.autovideocreator.configuration.VariablesConfig;
import org.creator.autovideocreator.exception.ExternalProgramExecutionException;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RequiredArgsConstructor
public class ScriptVideoMontageTool {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static File createVideo(String mainVideoId, String otherVideoId, String outputVideoName,
                                   int startVideoTime, int endVideoTime, boolean isOverlay) {
        File outputFile = new File(outputVideoName + ".mp4");

        Future<?> future = executorService.submit(() -> {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(
                        "python", VariablesConfig.getStaticScriptPath(), mainVideoId,
                        otherVideoId, String.valueOf(startVideoTime), String.valueOf(endVideoTime),
                        outputVideoName, String.valueOf(isOverlay));

                processBuilder.redirectErrorStream(true);
                processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
//                String resourcesPath = ScriptVideoMontageTool.class
//                        .getClassLoader().getResource("creator_script").getPath();
//                File workingDir = new File("src/main/resources/creator_script");
//                processBuilder.directory(workingDir);

                Process process = processBuilder.start();
                int exitCode = process.waitFor();

                if (exitCode != 0) {
                    throw new ExternalProgramExecutionException("Python script execution failed with exit code: " + exitCode);
                }
            } catch (Exception e) {
                throw new ExternalProgramExecutionException("Problem during video creation");
            }
        });

        try {
            future.get();
        } catch (Exception e) {
            throw new RuntimeException("Error during video processing", e);
        }

        if (!outputFile.exists()) {
            throw new RuntimeException("Output file was not created: " + outputFile.getAbsolutePath());
        }

        return outputFile;
    }
}

