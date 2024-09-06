package org.creator.autovideocreator.instrument;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class ProcessExecutor {
    public boolean execute(List<String> command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        var reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        int exitCode = process.waitFor();

        if (exitCode == 0) {
            System.out.println("Execution ended successfully");
            return true;
        } else {
            System.err.println("Process failed with exit code: " + exitCode);
            return false;
        }
    }
}
