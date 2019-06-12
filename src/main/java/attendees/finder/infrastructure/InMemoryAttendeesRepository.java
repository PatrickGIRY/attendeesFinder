package attendees.finder.infrastructure;

import attendees.finder.domain.Attendee;
import attendees.finder.domain.Attendees;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class InMemoryAttendeesRepository implements Attendees {
    private final List<Attendee> attendees = new ArrayList<>();

    @Override
    public void append(Attendee attendee) {
        this.attendees.add(Attendee.copyOf(attendee));
    }

    @Override
    public List<Attendee> findByInfixOfFirstName(String query) {
        List<Attendee> result = new ArrayList<>();
        final Predicate<Attendee> predicate = matches(query);
        final Consumer<Attendee> append = result::add;
        for (Attendee attendee : attendees) {
            final var consumer = addIf(predicate, attendee, append);
            consumer.accept(attendee);
        }
        return result;
    }

    private Consumer<Attendee> addIf(Predicate<Attendee> predicate, Attendee attendee, Consumer<Attendee> append) {
        if (predicate.test(attendee)) {
            return append;
        } else {
            return attendee1 -> {};
        }
    }

    private Predicate<Attendee> matches(String query) {
        return attendee -> attendee.isFirstNameInfixOf(query);
    }
}
