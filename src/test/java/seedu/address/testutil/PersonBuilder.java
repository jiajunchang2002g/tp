package seedu.address.testutil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.Address;
import seedu.address.model.person.Alias;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.person.Risk;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Address address;
    private seedu.address.model.person.Stage stage;
    private List<Alias> aliases;
    private Notes notes;
    private Risk risk;
    private Set<Tag> tags;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        address = new Address(DEFAULT_ADDRESS);
        stage = seedu.address.model.person.Stage.SURVEILLANCE;
        aliases = List.of();
        notes = new Notes("");
        risk = Risk.getDefault();
        tags = Set.of();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        address = personToCopy.getAddress();
        stage = personToCopy.getStage();
        aliases = personToCopy.getAliases();
        notes = personToCopy.getNotes();
        risk = personToCopy.getRisk();
        tags = personToCopy.getTags();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Risk} of the {@code Person} that we are building.
     */
    public PersonBuilder withRisk(String risk) {
        this.risk = seedu.address.model.person.Risk.fromString(risk);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Stage} of the {@code Person} that we are building.
     */
    public PersonBuilder withStage(String stage) {
        this.stage = seedu.address.model.person.Stage.fromString(stage);
        return this;
    }

    /**
     * Sets the aliases of the {@code Person} that we are building.
     */
    public PersonBuilder withAliases(String... aliases) {
        this.aliases = java.util.Arrays.stream(aliases)
                .map(Alias::new)
                .toList();
        return this;
    }

    /**
     * Sets the {@code Notes} of the {@code Person} that we are building.
     */
    public PersonBuilder withNotes(String notes) {
        this.notes = new Notes(notes);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String... tags) {
        this.tags = java.util.Arrays.stream(tags)
                .map(Tag::new)
                .collect(Collectors.toSet());
        return this;
    }

    public Person build() {
        return new Person(name, address, stage, aliases, notes, risk, tags);
    }

}
