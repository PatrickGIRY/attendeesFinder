package attendees.finder.infrastructure;

import attendees.finder.domain.Attendee;
import attendees.finder.domain.Attendees;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InMemoryAttendeesRepository implements Attendees {
    private final List<Attendee> attendees = new ArrayList<>();

    @Override
    public void append(Attendee attendee) {
        this.attendees.add(Attendee.copyOf(attendee));
    }

    @Override
    public List<Attendee> findByInfixOfFirstName(String query) {
        List<Attendee> result = new ArrayList<>();
        for (Attendee attendee : attendees) {
            if (matches(query, attendee)) {
                result.add(attendee);
            }
        }
        return result;
    }

    private boolean matches(String query, Attendee attendee) {
        return attendee.isFirstNameInfixOf(query);
    }
}
