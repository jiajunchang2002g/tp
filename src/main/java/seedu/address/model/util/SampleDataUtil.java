package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;
import seedu.address.model.person.Risk;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                seedu.address.model.person.Stage.SURVEILLANCE,
                java.util.List.of(),
                new Notes(""),
                Risk.getDefault()),
            new Person(new Name("Bernice Yu"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                seedu.address.model.person.Stage.APPROACHED,
                java.util.List.of(),
                new Notes(""),
                Risk.getDefault()),
            new Person(new Name("Charlotte Oliveiro"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                seedu.address.model.person.Stage.COOPERATING,
                java.util.List.of(),
                new Notes(""),
                Risk.getDefault()),
            new Person(new Name("David Li"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                seedu.address.model.person.Stage.ARRESTED,
                java.util.List.of(),
                new Notes(""),
                Risk.getDefault()),
            new Person(new Name("Irfan Ibrahim"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                seedu.address.model.person.Stage.SURVEILLANCE,
                java.util.List.of(),
                new Notes(""),
                Risk.getDefault()),
            new Person(new Name("Roy Balakrishnan"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                seedu.address.model.person.Stage.CLOSED,
                java.util.List.of(),
                new Notes(""),
                Risk.getDefault())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

}
