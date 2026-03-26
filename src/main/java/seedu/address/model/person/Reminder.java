package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a reminder attached to a contact.
 * Guarantees: immutable; date/time/note are present and valid.
 */
public class Reminder implements Comparable<Reminder> {

    public static final String MESSAGE_NOTE_CONSTRAINTS = "Reminder note should not be blank";
    public static final String NOTE_VALIDATION_REGEX = "[^\\s].*";

    private final LocalDate date;
    private final LocalTime time;
    private final String note;

    /**
     * Creates a {@code Reminder}.
     */
    public Reminder(LocalDate date, LocalTime time, String note) {
        requireNonNull(date);
        requireNonNull(time);
        requireNonNull(note);
        String trimmedNote = note.trim();
        checkArgument(isValidNote(trimmedNote), MESSAGE_NOTE_CONSTRAINTS);

        this.date = date;
        this.time = time;
        this.note = trimmedNote;
    }

    public static boolean isValidNote(String test) {
        return test != null && test.matches(NOTE_VALIDATION_REGEX);
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getNote() {
        return note;
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(date, time);
    }

    @Override
    public int compareTo(Reminder other) {
        return this.getDateTime().compareTo(other.getDateTime());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Reminder)) {
            return false;
        }

        Reminder otherReminder = (Reminder) other;
        return date.equals(otherReminder.date)
                && time.equals(otherReminder.time)
                && note.equals(otherReminder.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time, note);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("date", date)
                .add("time", time)
                .add("note", note)
                .toString();
    }
}
