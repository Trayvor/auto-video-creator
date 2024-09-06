package org.creator.autovideocreator.exception;

public class YoutubeAuthorizationException extends RuntimeException {
    public YoutubeAuthorizationException(String message, Throwable exception) {
        super(message, exception);
    }

    public YoutubeAuthorizationException(String message) {
        super(message);
    }
}