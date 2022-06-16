// Based off https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.Calendar;
import model.Event;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Calendar calendar = new Calendar("Adam's Calendar");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Calendar calendar = new Calendar("Adam's Calendar");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCalendar.json");
            writer.open();
            writer.write(calendar);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCalendar.json");
            calendar = reader.read();
            assertEquals("Adam's Calendar", calendar.getName());
            assertEquals(0, calendar.getListOfEvents().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Calendar calendar = new Calendar("Adam's Calendar");

            LocalDateTime startDateTime = LocalDateTime.of(2021, 10, 28, 18, 30);
            LocalDateTime endDateTime = LocalDateTime.of(2021, 10, 28, 18, 45);
            Event event1 = new Event("Soccer", "UBC", startDateTime, endDateTime);
            calendar.addEvent(event1);

            startDateTime = LocalDateTime.of(2021, 11, 28, 18, 30);
            endDateTime = LocalDateTime.of(2021, 11, 28, 18, 45);
            Event event2 = new Event("Swimming", "UBC", startDateTime, endDateTime);
            calendar.addEvent(event2);

            startDateTime = LocalDateTime.of(2021, 12, 28, 18, 30);
            endDateTime = LocalDateTime.of(2021, 12, 28, 18, 45);
            Event event3 = new Event("Tennis", "UBC", startDateTime, endDateTime);
            calendar.addEvent(event3);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCalendar.json");
            writer.open();
            writer.write(calendar);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCalendar.json");
            calendar = reader.read();
            assertEquals("Adam's Calendar", calendar.getName());
            ArrayList<Event> events = calendar.getListOfEvents();
            assertEquals(3, events.size());
            checkEvent(events.get(0), event1.getDescription(), event1.getLocation(),
                       event1.getStartDateTime(), event1.getEndDateTime(), event1.getRecurringUntil());
            checkEvent(events.get(1), event2.getDescription(), event2.getLocation(),
                       event2.getStartDateTime(), event2.getEndDateTime(), event2.getRecurringUntil());
            checkEvent(events.get(2), event3.getDescription(), event3.getLocation(),
                    event3.getStartDateTime(), event3.getEndDateTime(), event3.getRecurringUntil());
            assertEquals(event1.getEventId(), events.get(0).getEventId());
            assertEquals(event2.getEventId(), events.get(1).getEventId());
            assertEquals(event3.getEventId(), events.get(2).getEventId());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
