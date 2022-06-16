package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    LocalDateTime time1;
    LocalDateTime time2;
    LocalDateTime time3;
    LocalDateTime time4;
    LocalDateTime time5;
    LocalDateTime time6;
    LocalDateTime time7;


    @BeforeEach
    void runBefore() {
        time1 = LocalDateTime.of(2021, 10, 15, 17, 0);
        time2 = LocalDateTime.of(2021, 10, 16, 5, 30);
        time3 = LocalDateTime.of(2021, 10, 27, 10, 5);
        time4 = LocalDateTime.of(2021, 11, 5, 0, 5);
        time5 = LocalDateTime.of(2021, 10, 15, 8, 30);
        time6 = LocalDateTime.of(2021, 10, 15, 10, 5);
        time7 = LocalDateTime.of(2021, 10, 16, 10, 5);
    }

    @Test
    void testConstructor() {
        Event testEvent = new Event("Swimming practice", "UBC", time1, time2);
        assertEquals("Swimming practice", testEvent.description);
        assertEquals("UBC", testEvent.location);
        assertEquals(time1, testEvent.startDateTime);
        assertEquals(time2, testEvent.endDateTime);
        assertEquals(time1, testEvent.recurringUntil);
        assertTrue(testEvent.eventId.length() > 0);
    }


    @Test
    void testConstructorWithRecurringUntilParameter() {
        Event testEvent = new Event("Swimming practice", "UBC", time1, time2, time4);
        assertEquals("Swimming practice", testEvent.description);
        assertEquals("UBC", testEvent.location);
        assertEquals(time1, testEvent.startDateTime);
        assertEquals(time2, testEvent.endDateTime);
        assertEquals(time4, testEvent.recurringUntil);
        assertTrue(testEvent.eventId.length() > 0);
    }


    @Test
    void testFormatEventSameDay() {
        Event eventToFormat = new Event("Swimming", "UBC aquatic center", time5, time6);
        String actualFormat = eventToFormat.formatEvent();

        String expectedFormat = "";
        expectedFormat = expectedFormat + "OCTOBER 15, 2021, 08:30 - 10:05 ";
        expectedFormat = expectedFormat + "Swimming ";
        expectedFormat = expectedFormat + "@ UBC aquatic center";
        //expectedFormat = expectedFormat + "EventID:     " + eventToFormat.eventId + "\n";

        assertEquals(expectedFormat, actualFormat);
    }

    @Test
    void testFormatEventDifferentDay() {
        Event eventToFormat = new Event("Swimming", "UBC aquatic center", time5, time7);
        String actualFormat = eventToFormat.formatEvent();

        String expectedFormat = "";
        expectedFormat = expectedFormat + "OCTOBER 15, 2021, 08:30 - OCTOBER 16, 2021, 10:05 ";
        expectedFormat = expectedFormat + "Swimming ";
        expectedFormat = expectedFormat + "@ UBC aquatic center";
        //expectedFormat = expectedFormat + "EventID:     " + eventToFormat.eventId + "\n";

        assertEquals(expectedFormat, actualFormat);

    }

}
