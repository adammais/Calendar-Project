// Based off https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.Calendar;
import model.Event;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Calendar read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCalendar(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Calendar from JSON object and returns it
    private Calendar parseCalendar(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Calendar calendar = new Calendar(name);
        addEvents(calendar, jsonObject);
        return calendar;
    }

    // MODIFIES: calendar
    // EFFECTS: parses thingies from JSON object and adds them to Calendar
    private void addEvents(Calendar calendar, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("listOfEvents");
        for (Object json : jsonArray) {
            JSONObject nextEvent = (JSONObject) json;
            addEvent(calendar, nextEvent);
        }
    }

    // MODIFIES: calendar
    // EFFECTS: parses thingy from JSON object and adds it to Calendar
    private void addEvent(Calendar calendar, JSONObject jsonObject) {
        String description = jsonObject.getString("description");
        String location = jsonObject.getString("location");

        String startDateTimeString = jsonObject.getString("startDateTime");
        LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        String endDateTimeString = jsonObject.getString("endDateTime");
        LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        String recurringUntilString = jsonObject.getString("recurringUntil");
        LocalDateTime recurringUntil = LocalDateTime.parse(recurringUntilString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        String eventId = jsonObject.getString("eventId");
        Event eventToAdd = new Event(description, location, startDateTime, endDateTime, recurringUntil, eventId);
        calendar.addEventFromRead(eventToAdd);
    }
}
