package attendees.finder.infrastructure;

import attendees.finder.domain.Attendee;
import attendees.finder.domain.Attendees;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("The attendees finder should return")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AttendeesFinderTest {

    private static final Attendee MARC = Attendee.withFirstName("Marc");
    private static final Attendee CHRISTELLE = Attendee.withFirstName("Christelle");
    private static final Attendee CHRISTOPHE = Attendee.withFirstName("Christophe");

    private Attendees attendees;

    @BeforeAll
    void setUp() {

        attendees = new InMemoryAttendeesRepository();
        attendees.append(MARC);
        attendees.append(CHRISTOPHE);
        attendees.append(CHRISTELLE);
    }

    @Test
    @DisplayName("an empty result when no attendee first name matches the query string")
    void no_attendee_matches() {

        List<Attendee> result = attendees.findByInfixOfFirstName("Paul");

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("one result when only one attendee first name matches the query string")
    void one_attendee_matches() {

        List<Attendee> result = attendees.findByInfixOfFirstName("Marc");

        assertThat(result).containsOnly(MARC);
    }

    @Test
    @DisplayName("all the results when many attendees first names matche the query string")
    void many_attendees_matche() {

        List<Attendee> result = attendees.findByInfixOfFirstName("hri");

        assertThat(result).containsOnly(CHRISTELLE, CHRISTOPHE);
    }

}
