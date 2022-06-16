package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Represents a log of alarm system events.
 * We use the Singleton Design Pattern to ensure that there is only
 * one EventLog in the system and that the system has global access
 * to the single instance of the EventLog.
 */
public class IncidentLog implements Iterable<Incident> {
    /** the only EventLog in the system (Singleton Design Pattern) */
    private static IncidentLog theLog;
    private Collection<Incident> incidents;

    /**
     * Prevent external construction.
     * (Singleton Design Pattern).
     */
    private IncidentLog() {
        incidents = new ArrayList<Incident>();
    }

    /**
     * Gets instance of EventLog - creates it
     * if it doesn't already exist.
     * (Singleton Design Pattern)
     * @return  instance of EventLog
     */
    public static IncidentLog getInstance() {
        if (theLog == null) {
            theLog = new IncidentLog();
        }
        return theLog;
    }

    /**
     * Adds an event to the event log.
     * @param e the event to be added
     */
    public void logIncident(Incident i) {
        incidents.add(i);
    }

    /**
     * Clears the event log and logs the event.
     */
    public void clear() {
        incidents.clear();
        logIncident(new Incident("Incident log cleared."));
    }

    @Override
    public Iterator<Incident> iterator() {
        return incidents.iterator();
    }
}