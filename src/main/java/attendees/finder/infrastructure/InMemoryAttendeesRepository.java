package attendees.finder.infrastructure;

import attendees.finder.domain.Attendee;
import attendees.finder.domain.Attendees;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class InMemoryAttendeesRepository implements Attendees {
    private final List<Attendee> attendees = new ArrayList<>();

    @Override
    public void append(Attendee attendee) {
        this.attendees.add(Attendee.copyOf(attendee));
    }

    @Override
    public List<Attendee> findByInfixOfFirstName(String query) {
        final Predicate<Attendee> predicate = matches(query);
        return filter(predicate, attendees);
    }

    private static List<Attendee> filter(Predicate<Attendee> predicate, List<Attendee> attendees) {
        List<Attendee> result = new ArrayList<>();
        for (Attendee attendee : attendees) {
            if (predicate.test(attendee)) {
                result.add(attendee);
            }
        }
        return result;
    }

    private Predicate<Attendee> matches(String query) {
        return attendee -> attendee.isFirstNameInfixOf(query);
    }
}
