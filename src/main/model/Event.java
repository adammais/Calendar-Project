package model;

import org.json.JSONObject;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

// Represents an Event having a description, location, start date, end date, date it stops recurring, and a unique ID.
public class Event implements Serializable {
    String description;
    String location;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    LocalDateTime recurringUntil;
    String eventId;
    Boolean addInRead;

    // REQUIRES: description has a non-zero length;
    // EFFECTS: description of the event is set to description; location of the event is set to location;
    //          startDateTime of the event is set to startDateTime; endDateTime of the event is set to endDateTime;
    //          the event's recurringUntil field is set to startDateTime; eventID of event is randomly generated.
    public Event(String description, String location, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.description = description;
        this.location = location;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.recurringUntil = startDateTime;
        this.eventId = UUID.randomUUID().toString();
    }

    // REQUIRES: description has a non-zero length and recurringUntilTime is after the startDateTime
    // EFFECTS: description of the event is set to description; location of the event is set to location;
    //          startDateTime of the event is set to startDateTime; endDateTime of the event is set to endDateTime;
    //          the event's recurringUntil field is set to startDateTime; eventID of event is randomly generated.
    public Event(String description,
                 String location,
                 LocalDateTime startDateTime,
                 LocalDateTime endDateTime,
                 LocalDateTime recurringUntil) {

        this.description = description;
        this.location = location;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.recurringUntil = recurringUntil;
        this.eventId = UUID.randomUUID().toString();
    }

    // REQUIRES: description has a non-zero length and recurringUntilTime is after the startDateTime
    // EFFECTS: description of the event is set to description; location of the event is set to location;
    //          startDateTime of the event is set to startDateTime; endDateTime of the event is set to endDateTime;
    //          the event's recurringUntil field is set to startDateTime; eventID of event is randomly generated.
    public Event(String description,
                 String location,
                 LocalDateTime startDateTime,
                 LocalDateTime endDateTime,
                 LocalDateTime recurringUntil,
                 String eventId) {

        this.description = description;
        this.location = location;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.recurringUntil = recurringUntil;
        this.eventId = eventId;
    }

    public String getDescription() {
        return this.description;
    }

    public String getLocation() {
        return this.location;
    }

    public LocalDateTime getStartDateTime() {
        return this.startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return this.endDateTime;
    }

    public LocalDateTime getRecurringUntil() {
        return this.recurringUntil;
    }

    public String getEventId() {
        return this.eventId;
    }

    // EFFECTS: returns a String representation of event
    public String formatEvent() {
        String eventString = "";
        eventString = eventString + formatDateTime() + " ";
        eventString = eventString + this.description + " ";
        eventString = eventString + "@ " + this.location;

        // eventString = eventString + "EventID:     " + this.eventId + "\n";
        return eventString;
    }

    // EFFECTS: returns a String representation of the event's Start and End Times
    private String formatDateTime() {
        LocalDate startDate = this.startDateTime.toLocalDate();
        LocalDate endDate = this.endDateTime.toLocalDate();
        LocalTime startTime = this.startDateTime.toLocalTime();
        LocalTime endTime = this.endDateTime.toLocalTime();
        if (startDate.equals(endDate)) {
            return formatDate(startDate) + ", " + formatTime(startTime) + " - " + formatTime(endTime);
        } else {
            return formatDate(startDate) + ", " + formatTime(startTime) + " - "
                                   + formatDate(endDate) + ", " + formatTime(endTime);
        }
    }

    // EFFECTS: returns a String representation of a date
    private String formatDate(LocalDate date) {
        return date.getMonth() + " " + date.getDayOfMonth() + ", " + date.getYear();
    }

    // EFFECTS: returns a String representation of a time
    private String formatTime(LocalTime time) {
        int hour = time.getHour();
        String formattedHour;
        if (hour <= 9) {
            formattedHour = "0" + hour;
        } else {
            formattedHour = Integer.toString(hour);
        }
        int minute = time.getMinute();
        String formattedMinute;
        if (minute <= 9) {
            formattedMinute = "0" + minute;
        } else {
            formattedMinute = Integer.toString(minute);
        }
        return formattedHour + ":" + formattedMinute;
    }

    public JSONObject eventToJson() {
        JSONObject json = new JSONObject();
        json.put("description", this.description);
        json.put("location", this.location);
        json.put("startDateTime", this.startDateTime);
        json.put("endDateTime", this.endDateTime);
        json.put("recurringUntil", this.recurringUntil);
        json.put("eventId", this.eventId);
        return json;
    }

    public String toString() {
        return this.formatEvent();
    }



}
