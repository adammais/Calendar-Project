package ui;

import model.Calendar;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

// Runs a console based calendar
public class CalendarApp {
    private static final String JSON_STORE = "./data/calendar.json";
    private Scanner input;
    private Calendar calendar;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the Calendar application
    public CalendarApp() {
        input = new Scanner(System.in);
        init();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runCalendar();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runCalendar() {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAdd();
        } else if (command.equals("r")) {
            doRemove();
        } else if (command.equals("d")) {
            doDisplay();
        } else if (command.equals("s")) {
            saveCalendar();
        } else if (command.equals("l")) {
            loadCalendar();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds an event to the calendar's list of events
    private void doAdd() {
        System.out.print("Enter the event description: ");
        String eventDescription = input.next();
        System.out.print("Enter the location of the event: ");
        String eventLocation = input.next();
        LocalDateTime startDate = promptForDateTime("starts");
        LocalDateTime endDate = promptForDateTime("ends");
        LocalDateTime recurringUntil = promptForIsRecurring();
        Event eventToAdd;
        if (recurringUntil != null) {
            eventToAdd = new Event(eventDescription, eventLocation, startDate, endDate, recurringUntil);
        } else {
            eventToAdd = new Event(eventDescription, eventLocation, startDate, endDate);
        }
        String eventID = calendar.addEvent(eventToAdd);
        // If the event is recurring eventID is ID of last instance of recurring event
        System.out.print("The eventID for the last event added is: " + eventID);
    }

    // REQUIRES: startsOrEndsOrRecursUntil is inputted as "starts", "ends", or "recurs until"
    // EFFECTS: prompts the user for the start, end, or recur until DateTime
    private LocalDateTime promptForDateTime(String startsOrEndsOrRecursUntil) {
        System.out.print("Enter the year the event " + startsOrEndsOrRecursUntil + ": ");
        int year = input.nextInt();
        System.out.print("Enter the month (from 1-12) the event " + startsOrEndsOrRecursUntil + ": ");
        int month = input.nextInt();
        System.out.print("Enter the day (1-31) the event " + startsOrEndsOrRecursUntil + ": ");
        int dayOfMonth = input.nextInt();
        System.out.print("Enter the hour (1-23) the event " + startsOrEndsOrRecursUntil + ": ");
        int hour = input.nextInt();
        System.out.print("Enter the minute (0-59) the event " + startsOrEndsOrRecursUntil + ": ");
        int minute = input.nextInt();
        return LocalDateTime.of(year, month, dayOfMonth, hour, minute);
    }

    // EFFECTS: asks the user if the event is recurring. If yes, prompts them for the time the event recurs until
    private LocalDateTime promptForIsRecurring() {
        System.out.print("Is this event recurring? Input y or n: ");
        String isRecurring = input.next();
        if (isRecurring.equals("y")) {
            return promptForDateTime("recurs until");
        } else {
            return null;
        }
    }

    // MODIFIES: this
    // EFFECTS: removes an event to the calendar's list of events
    private void doRemove() {
        System.out.print("Enter the eventID of the event you want to remove: ");
        String eventID = input.next();
        Boolean wasRemoved = calendar.removeEvent(eventID);
        if (wasRemoved) {
            System.out.println("The event was successfully removed");
        } else {
            System.out.println("ERROR! The event could not be found in the Calendar");
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the calendar
    private void doDisplay() {
        calendar.printCalendar();
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        System.out.print("Enter Calendar Name: ");
        String calendarName = input.next();
        calendar = new Calendar(calendarName);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add Event");
        System.out.println("\tr -> Remove Event");
        System.out.println("\td -> Display Calendar");
        System.out.println("\ts -> Save Calendar");
        System.out.println("\tl -> Load Calendar");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: saves the calendar to file
    private void saveCalendar() {
        try {
            jsonWriter.open();
            jsonWriter.write(calendar);
            jsonWriter.close();
            System.out.println("Saved " + this.calendar.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Calendar from file
    private void loadCalendar() {
        try {
            calendar = jsonReader.read();
            System.out.println("Loaded " + calendar.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
