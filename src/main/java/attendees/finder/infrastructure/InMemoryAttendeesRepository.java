package attendees.finder.infrastructure;

import attendees.finder.domain.Attendee;
import attendees.finder.domain.Attendees;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
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
        final BiFunction<Attendee, List<Attendee>, List<Attendee>> concat = this::concat;
        List<Attendee> result = new ArrayList<>();
        for (Attendee attendee : attendees) {
            final var fn = addIf(predicate, attendee, concat);
            result = fn.apply(attendee, result);
        }
        return result;
    }

    private List<Attendee> concat(Attendee attendee, List<Attendee> attendees) {
        var newAttendees = new ArrayList<>(attendees);
        newAttendees.add(attendee);
        return newAttendees;
    }

    private BiFunction<Attendee, List<Attendee>, List<Attendee>> addIf(Predicate<Attendee> predicate, Attendee attendee, BiFunction<Attendee, List<Attendee>, List<Attendee>> concat) {
        if (predicate.test(attendee)) {
            return concat;
        } else {
            return (attendee1, attendees) -> attendees;
        }
    }

    private Predicate<Attendee> matches(String query) {
        return attendee -> attendee.isFirstNameInfixOf(query);
    }
}
