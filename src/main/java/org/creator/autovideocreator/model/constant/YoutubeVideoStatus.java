package org.creator.autovideocreator.model.constant;

public enum YoutubeVideoStatus {
    TO_CROP("to_crop"),
    CROPPED("cropped");

    private final String displayName;

    YoutubeVideoStatus(String displayName) {
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
