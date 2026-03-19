package seedu.address.model.person;

/**
 * Represents a Person's risk level.
 */
public enum Risk {
    LOW,
    MEDIUM,
    HIGH;

    public static final String MESSAGE_CONSTRAINTS =
            "Invalid risk. Risk must be one of: low, medium, high.";

    public static Risk fromString(String raw) {
        if (raw == null) {
            throw new IllegalArgumentException("Risk is null");
        }
        String normalised = raw.trim().toUpperCase();
        return Risk.valueOf(normalised);
    }

    public static Risk getDefault() {
        return MEDIUM;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}

