// Based off https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.Calendar;
import model.Event;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.time.LocalDateTime;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Calendar calendar = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCalendar() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCalendar.json");
        try {
            Calendar calendar = reader.read();
            assertEquals("Adam's Calendar", calendar.getName());
            assertEquals(0, calendar.getListOfEvents().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCalendar() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCalendar.json");
        try {
            Calendar calendar = reader.read();
            assertEquals("Adam's Calendar", calendar.getName());
            List<Event> events = calendar.getListOfEvents();
            assertEquals(3, events.size());

            LocalDateTime startDateTime = LocalDateTime.of(2021, 10, 28, 18, 30);
            LocalDateTime endDateTime = LocalDateTime.of(2021, 10, 28, 18, 45);
            Event actualEvent1 = events.get(0);
            checkEvent(actualEvent1, "Soccer", "UBC", startDateTime, endDateTime, endDateTime);

            startDateTime = LocalDateTime.of(2021, 11, 28, 18, 30);
            endDateTime = LocalDateTime.of(2021, 11, 28, 18, 45);
            Event actualEvent2 = events.get(1);
            checkEvent(actualEvent2, "Swimming", "UBC", startDateTime, endDateTime, endDateTime);

            startDateTime = LocalDateTime.of(2021, 12, 28, 18, 30);
            endDateTime = LocalDateTime.of(2021, 12, 28, 18, 45);
            Event actualEvent3 = events.get(2);
            checkEvent(actualEvent3, "Tennis", "UBC", startDateTime, endDateTime, endDateTime);

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
