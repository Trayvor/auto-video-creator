package org.creator.autovideocreator.model.constant;

public enum InternalVideoStatus {
    TO_POST("to_post"),
    POSTED("posted");

    private final String displayName;

    InternalVideoStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
