package attendees.finder.domain;

import java.util.Objects;

public class Attendee {
    private final String firstName;

    public static Attendee withFirstName(String firstName) {
        return new Attendee(firstName);
    }

    public static Attendee copyOf(Attendee attendee) {
        return withFirstName(attendee.firstName);
    }

    private Attendee(String firstName) {
        this.firstName = firstName;
    }

    public boolean isFirstNameInfixOf(String query) {
        return firstName.contains(query);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attendee)) return false;
        Attendee attendee = (Attendee) o;
        return Objects.equals(firstName, attendee.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName);
    }

    @Override
    public String toString() {
        return "Attendee{" +
                "firstName='" + firstName + '\'' +
                '}';
    }
}
