package org.creator.autovideocreator.exception;

public class SearchingException extends RuntimeException {
    public SearchingException(String message, Throwable exception) {
        super(message, exception);
    }
}
