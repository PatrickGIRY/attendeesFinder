package attendees.finder.infrastructure;

import attendees.finder.domain.Attendee;
import attendees.finder.domain.Attendees;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
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
        return filter(predicate);
    }

    private List<Attendee> filter(Predicate<Attendee> predicate) {
        final Function<Attendee, Function<List<Attendee>, List<Attendee>>> concat = attendee -> attendees -> concat(attendee, attendees);
        List<Attendee> result = new ArrayList<>();
        for (Attendee attendee : attendees) {
            result = addIf(predicate, attendee, concat).apply(result);
        }
        return result;
    }

    private List<Attendee> concat(Attendee attendee, List<Attendee> attendees) {
        var newAttendees = new ArrayList<>(attendees);
        newAttendees.add(attendee);
        return newAttendees;
    }

    private Function<List<Attendee>, List<Attendee>> addIf(Predicate<Attendee> predicate, Attendee attendee, Function<Attendee, Function<List<Attendee>, List<Attendee>>> concat) {
        if (predicate.test(attendee)) {
            return concat.apply(attendee);
        } else {
            return attendees -> attendees;
        }
    }

    private Predicate<Attendee> matches(String query) {
        return attendee -> attendee.isFirstNameInfixOf(query);
    }
}
