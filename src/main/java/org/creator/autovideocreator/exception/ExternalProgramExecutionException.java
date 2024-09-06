package org.creator.autovideocreator.exception;

public class ExternalProgramExecutionException extends RuntimeException {
    public ExternalProgramExecutionException(String message, Throwable error) {
        super(message, error);
    }
}
