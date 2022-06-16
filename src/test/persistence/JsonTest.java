package persistence;

import model.Event;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonTest {
    protected void  checkEvent(Event event,
                               String description,
                               String location,
                               LocalDateTime startDateTime,
                               LocalDateTime endDateTime,
                               LocalDateTime recurringUntil) {

        assertEquals(description, event.getDescription());
        assertEquals(location, event.getLocation());
        assertEquals(startDateTime, event.getStartDateTime());
        assertEquals(endDateTime, event.getEndDateTime());
        assertEquals(recurringUntil, event.getRecurringUntil());
        assertTrue(!event.getEventId().isEmpty());
    }
}
