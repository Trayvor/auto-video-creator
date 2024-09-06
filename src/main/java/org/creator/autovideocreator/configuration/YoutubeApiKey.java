package org.creator.autovideocreator.configuration;

import com.google.api.services.youtube.YouTube;
import org.creator.autovideocreator.exception.YoutubeAuthorizationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class YoutubeApiKey {
    private static final String PROPERTIES_FILENAME = "youtube.properties";
    private static final String PROPERTIES_APIKEY_PROPERTY = "youtube.apikey";

    public static String getApiKey() {
        Properties properties = new Properties();
        InputStream inputStream = YouTube.Search.class.getResourceAsStream("/"
                + PROPERTIES_FILENAME);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            new YoutubeAuthorizationException("Error during reading " + PROPERTIES_FILENAME, e);
        }
        String apiKey = properties.getProperty(PROPERTIES_APIKEY_PROPERTY);
        return apiKey;
    }
}
