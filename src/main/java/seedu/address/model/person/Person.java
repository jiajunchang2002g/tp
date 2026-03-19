package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Stage stage;

    // Data fields
    private final Address address;
    private final List<Alias> aliases;
    private final Notes notes;
    private final Risk risk;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Address address, Stage stage, List<Alias> aliases, Notes notes, Risk risk) {
        requireAllNonNull(name, address, stage, aliases, notes, risk);
        this.name = name;
        this.address = address;
        this.stage = stage;
        this.aliases = List.copyOf(aliases);
        this.notes = notes;
        this.risk = risk;
    }

    public Name getName() {
        return name;
    }   

    public Address getAddress() {
        return address;
    }

    public List<Alias> getAliases() {
        return Collections.unmodifiableList(aliases);
    }

    public Notes getNotes() {
        return notes;
    }

    public Stage getStage() {
        return stage;
    }

    public Risk getRisk() {
        return risk;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && address.equals(otherPerson.address)
                && stage.equals(otherPerson.stage)
                && aliases.equals(otherPerson.aliases)
                && notes.equals(otherPerson.notes)
                && risk.equals(otherPerson.risk);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, address, stage, aliases, notes, risk);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("address", address)
                .add("aliases", aliases)
                .add("notes", notes)
                .add("stage", stage)
                .add("risk", risk)
                .toString();
    }

}
