package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CalendarTest {
    static final String CALENDAR_NAME = "Test Calendar";
    private Calendar testCalendar;
    private Event eventToAdd1;
    private Event eventToAdd2;
    private Event eventToAdd3;

    @BeforeEach
    void runBefore() {
        testCalendar = new Calendar(CALENDAR_NAME);
        eventToAdd1 = new Event("Swimming", "UBC aquatic center",
            LocalDateTime.of(2021, 10, 15, 18, 30),
            LocalDateTime.of(2021, 10, 15, 19, 30));
        eventToAdd2 = new Event("Soccer", "Thunderbird Stadium",
            LocalDateTime.of(2021, 10, 16, 18, 30),
            LocalDateTime.of(2021, 10, 16, 19, 30));
        eventToAdd3 = new Event("Soccer Practice", "Thunderbird Stadium",
            LocalDateTime.of(2021, 10, 14, 18, 30),
            LocalDateTime.of(2021, 10, 14, 19, 30),
            LocalDateTime.of(2021, 10, 28, 18, 30));
    }

    @Test
    void testConstructor() {
        assertEquals(CALENDAR_NAME, testCalendar.name);
        assertEquals(true, testCalendar.listOfEvents.isEmpty());
    }
    @Test
    void testAddSingleEvent() {
        String eventID = testCalendar.addEvent(eventToAdd1);
        assertEquals(1, testCalendar.listOfEvents.size());
        assertEquals(eventID, testCalendar.listOfEvents.get(0).eventId);
        assertEquals(eventToAdd1, testCalendar.listOfEvents.get(0));
    }

    @Test
    void testAddMultipleEvents() {
        testCalendar.addEvent(eventToAdd1);
        testCalendar.addEvent(eventToAdd2);
        assertEquals(2, testCalendar.listOfEvents.size());
        assertEquals(eventToAdd1, testCalendar.listOfEvents.get(0));
        assertEquals(eventToAdd2, testCalendar.listOfEvents.get(1));
    }

    @Test
    void testAddRecurringEvent() {
        testCalendar.addEvent(eventToAdd3);
        // testCalendar.printCalendar();
        assertEquals(3, testCalendar.listOfEvents.size());
    }

    @Test
    void testRemoveSingleEvent() {
        assertEquals(0, testCalendar.listOfEvents.size());
        String eventID = testCalendar.addEvent(eventToAdd1);
        assertEquals(1, testCalendar.listOfEvents.size());
        Boolean isSuccess = testCalendar.removeEvent(eventID);
        assertTrue(isSuccess);
        assertEquals(0, testCalendar.listOfEvents.size());
    }

    @Test
    void testRemoveNonExistentEvent() {
        assertEquals(0, testCalendar.listOfEvents.size());
        String eventID = testCalendar.addEvent(eventToAdd1);
        assertEquals(1, testCalendar.listOfEvents.size());
        Boolean isSuccess = testCalendar.removeEvent("junk");
        assertFalse(isSuccess);
        assertEquals(1, testCalendar.listOfEvents.size());
    }

    @Test
    void testEmptyCalendar() {
        String expectedCalendar = CALENDAR_NAME + "\n" + "No Events!\n";
        assertEquals(expectedCalendar, testCalendar.printCalendar());
    }

    @Test
    void testPrintCalendarWithOneEvent() {
        testCalendar.addEvent(eventToAdd1);
        String actualCalendar = testCalendar.printCalendar();
        String expectedCalendar = CALENDAR_NAME + "\n";
        expectedCalendar = expectedCalendar + testCalendar.listOfEvents.get(0).formatEvent() +"\n";
        assertEquals(expectedCalendar, actualCalendar);
    }

    @Test
    void testPrintCalendarWithMultipleEvents() {
        testCalendar.addEvent(eventToAdd1);
        testCalendar.addEvent(eventToAdd2);
        testCalendar.addEvent(eventToAdd3);
        String actualCalendar = testCalendar.printCalendar();
        String expectedCalendar = CALENDAR_NAME + "\n";
        expectedCalendar = expectedCalendar + testCalendar.listOfEvents.get(0).formatEvent() +"\n";
        expectedCalendar = expectedCalendar + testCalendar.listOfEvents.get(1).formatEvent() +"\n";
        expectedCalendar = expectedCalendar + testCalendar.listOfEvents.get(2).formatEvent() +"\n";
        expectedCalendar = expectedCalendar + testCalendar.listOfEvents.get(3).formatEvent() +"\n";
        expectedCalendar = expectedCalendar + testCalendar.listOfEvents.get(4).formatEvent() +"\n";
        assertEquals(expectedCalendar, actualCalendar);
    }

}