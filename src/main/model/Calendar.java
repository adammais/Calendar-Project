package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// represents a calendar with a name and a list of events
public class Calendar {

    String name;
    ArrayList<Event> listOfEvents;

    // REQUIRES: name has a non-zero length
    // EFFECTS: name of the calendar is set to name, the Calendar's listOfEvents is initialized to an empty ArrayList
    public Calendar(String name) {
        this.name = name;
        this.listOfEvents = new ArrayList<Event>();
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Event> getListOfEvents() {
        return this.listOfEvents;
    }

    public void addEventFromRead(Event eventToAdd) {
        this.listOfEvents.add(eventToAdd);
    }

    // MODIFIES: this
    // EFFECTS: the eventToAdd is added to the Calendar's listOfEvents. The event's unique eventID is returned
    public String addEvent(Event eventToAdd) {
        this.listOfEvents.add(eventToAdd);
        IncidentLog.getInstance().logIncident(new Incident("An Event has been added to the calendar."));
        // if the event is recurring every week add the same event every week until it stops recurring
        if (eventToAdd.recurringUntil.isAfter(eventToAdd.startDateTime.plusDays(7))
                || eventToAdd.recurringUntil.isEqual(eventToAdd.startDateTime.plusDays(7))) {
            addRecurringEvent(eventToAdd);
        }
        sortCalendar();
        return eventToAdd.eventId;
    }

    // MODIFIES: this
    // EFFECTS: if an event with a matching eventID is found, it is removed. A boolean value is returned to
    //          tell us if an event was removed
    public Boolean removeEvent(String eventId) {
        Boolean isSuccess = this.listOfEvents.removeIf(obj -> obj.eventId.equals(eventId));
        sortCalendar();
        if (isSuccess) {
            IncidentLog.getInstance().logIncident(new Incident("An Event has been removed from the calendar."));
        }
        return  isSuccess;
    }

    // MODIFIES: this
    // EFFECTS: The calendar's list of events is sorted then the calendar is printed
    public String printCalendar() {
        sortCalendar();
        String calendarPrintOut = "";
        calendarPrintOut = calendarPrintOut + this.name + "\n";
        if (this.listOfEvents.size() == 0) {
            calendarPrintOut = calendarPrintOut + "No Events!\n";
        }
        for (Event event : listOfEvents) {
            calendarPrintOut = calendarPrintOut + event.formatEvent() + "\n";
        }
        System.out.print(calendarPrintOut);
        return calendarPrintOut;
    }

    // MODIFIES: this
    // EFFECTS: adds a new recurringEvent to the calendar's list of events
    private void addRecurringEvent(Event eventToAdd) {
        Event recurringEvent = new Event(eventToAdd.description, eventToAdd.location,
                eventToAdd.startDateTime.plusDays(7), eventToAdd.endDateTime.plusDays(7),
                eventToAdd.recurringUntil);
        addEvent(recurringEvent);
    }

    // MODIFIES: this
    // EFFECTS: the calendar's list of events is sorted by date
    private void sortCalendar() {
        Collections.sort(this.listOfEvents, new Comparator<Event>() {
            public int compare(Event event1, Event event2) {
                return event1.startDateTime.compareTo(event2.startDateTime);
            }
        });
    }

    public JSONObject calendarToJson() {
        JSONObject json = new JSONObject();
        json.put("name", this.name);
        json.put("listOfEvents", listOfEventsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray listOfEventsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Event e : this.listOfEvents) {
            jsonArray.put(e.eventToJson());
        }

        return jsonArray;
    }

}
