package attendees.finder.infrastructure;

import attendees.finder.domain.Attendee;
import attendees.finder.domain.Attendees;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class InMemoryAttendeesRepository implements Attendees {
    private final List<Attendee> attendees = new ArrayList<>();

    @Override
    public void append(Attendee attendee) {
        this.attendees.add(Attendee.copyOf(attendee));
    }

    @Override
    public List<Attendee> findByInfixOfFirstName(String query) {
        List<Attendee> result = new ArrayList<>();
        final BiPredicate<String, Attendee> predicate = this::matches;
        for (Attendee attendee : attendees) {
            addIf(predicate, query, result, attendee);
        }
        return result;
    }

    private void addIf(BiPredicate<String, Attendee> predicate, String query, List<Attendee> result, Attendee attendee) {
        if (predicate.test(query, attendee)) {
            result.add(attendee);
        }
    }

    private boolean matches(String query, Attendee attendee) {
        return attendee.isFirstNameInfixOf(query);
    }
}
