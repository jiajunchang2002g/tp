package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Alias;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.person.Risk;
import seedu.address.model.person.Stage;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String address;
    private final String stage;
    private final List<String> aliases = new ArrayList<>();
    private final String notes;
    private final String risk;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name,
            @JsonProperty("address") String address,
            @JsonProperty("stage") String stage,
            @JsonProperty("aliases") List<String> aliases,
            @JsonProperty("notes") String notes,
            @JsonProperty("risk") String risk) {
        this.name = name;
        this.address = address;
        this.stage = stage;
        if (aliases != null) {
            this.aliases.addAll(aliases);
        }
        this.notes = notes;
        this.risk = risk;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        address = source.getAddress().value;
        stage = source.getStage().toString();
        aliases.addAll(source.getAliases().stream()
                .map(a -> a.value)
                .collect(Collectors.toList()));
        notes = source.getNotes().value;
        risk = source.getRisk().toString();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Alias> personAliases = new ArrayList<>();
        for (String alias : aliases) {
            if (alias == null || !Alias.isValidAlias(alias.trim())) {
                throw new IllegalValueException(Alias.MESSAGE_CONSTRAINTS);
            }
            personAliases.add(new Alias(alias));
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Notes modelNotes;
        if (notes == null) {
            modelNotes = new Notes("");
        } else {
            if (!Notes.isValidNotes(notes)) {
                throw new IllegalValueException(Notes.MESSAGE_CONSTRAINTS);
            }
            modelNotes = new Notes(notes);
        }

        final Risk modelRisk;
        if (risk == null) {
            modelRisk = Risk.getDefault();
        } else {
            try {
                modelRisk = Risk.fromString(risk);
            } catch (IllegalArgumentException ex) {
                throw new IllegalValueException(Risk.MESSAGE_CONSTRAINTS);
            }
        }

        if (stage == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Stage.class.getSimpleName()));
        }
        final Stage modelStage;
        try {
            modelStage = Stage.fromString(stage);
        } catch (IllegalArgumentException ex) {
            throw new IllegalValueException(Stage.MESSAGE_CONSTRAINTS);
        }

        return new Person(modelName, modelAddress, modelStage, personAliases, modelNotes, modelRisk);
    }

}
