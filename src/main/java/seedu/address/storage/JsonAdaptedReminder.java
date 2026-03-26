package seedu.address.storage;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Reminder;

/**
 * Jackson-friendly version of {@link Reminder}.
 */
class JsonAdaptedReminder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Reminder's %s field is missing!";

    private final String date;
    private final String time;
    private final String note;

    @JsonCreator
    public JsonAdaptedReminder(
            @JsonProperty("date") String date,
            @JsonProperty("time") String time,
            @JsonProperty("note") String note) {
        this.date = date;
        this.time = time;
        this.note = note;
    }

    public JsonAdaptedReminder(Reminder source) {
        this.date = source.getDate().toString();
        this.time = source.getTime().toString();
        this.note = source.getNote();
    }

    /**
     * Converts this Jackson-friendly adapted reminder object into the model's {@code Reminder} object.
     */
    public Reminder toModelType() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "date"));
        }
        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "time"));
        }
        if (note == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "note"));
        }

        final LocalDate modelDate;
        final LocalTime modelTime;
        try {
            modelDate = LocalDate.parse(date);
        } catch (Exception e) {
            throw new IllegalValueException("Invalid reminder date format: " + date);
        }
        try {
            modelTime = LocalTime.parse(time);
        } catch (Exception e) {
            throw new IllegalValueException("Invalid reminder time format: " + time);
        }
        if (!Reminder.isValidNote(note.trim())) {
            throw new IllegalValueException(Reminder.MESSAGE_NOTE_CONSTRAINTS);
        }
        return new Reminder(modelDate, modelTime, note);
    }
}
