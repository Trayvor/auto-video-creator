package org.creator.autovideocreator.model.constant;

public enum AccountType {
    YOUTUBE("Youtube"),
    INSTAGRAM("Instagram");

    private final String displayName;

    AccountType(String displayName) {
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
