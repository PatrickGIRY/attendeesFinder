package attendees.finder.domain;

import java.util.List;

public interface Attendees {
    void append(Attendee attendee);

    List<Attendee> findByInfixOfFirstName(String query);
}
