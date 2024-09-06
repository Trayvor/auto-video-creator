package org.creator.autovideocreator.exception;

public class TokenRefreshingException extends RuntimeException {
    public TokenRefreshingException(String message, Throwable error) {
        super(message, error);
    }
}