package org.creator.autovideocreator.configuration;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VariablesConfig {
    @Value("${video.creator.script.path}")
    private String scriptPath;
    @Getter
    private static String staticScriptPath;

    @PostConstruct
    public void init() {
        staticScriptPath = scriptPath;
    }
}
