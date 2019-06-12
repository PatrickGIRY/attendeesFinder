package attendees.finder.infrastructure;

import attendees.finder.domain.Attendee;
import attendees.finder.domain.Attendees;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InMemoryAttendeesRepository implements Attendees {
    private final List<Attendee> attendees = new ArrayList<>();

    @Override
    public void append(Attendee attendee) {
        this.attendees.add(Attendee.copyOf(attendee));
    }

    @Override
    public List<Attendee> findByInfixOfFirstName(String query) {
        return filter(matches(query), attendees);
    }

    private static List<Attendee> filter(Predicate<Attendee> predicate, List<Attendee> attendees) {
        return attendees.stream().filter(predicate).collect(Collectors.toList());
    }

    private Predicate<Attendee> matches(String query) {
        return attendee -> attendee.isFirstNameInfixOf(query);
    }
}
