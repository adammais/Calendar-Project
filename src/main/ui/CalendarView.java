package ui;

import model.Calendar;
import model.Event;

import java.util.ArrayList;
import javax.swing.*;

// Represents a DefaultListModel used to display Calendar
public class CalendarView {
    private DefaultListModel listModel;

    // MODIFIES: this
    // EFFECTS: initializes the listModel
    CalendarView() {
        listModel = new DefaultListModel();
    }

    // MODIFIES: this
    // EFFECTS: Display the calendar this expects the calendar to return a sorted list of events.
    public DefaultListModel getCalendarForList(Calendar calendar) {
        ArrayList<Event> listOfEvents = calendar.getListOfEvents();
        listModel.clear();
        for (int i = 0; i < listOfEvents.size(); i++) {
            listModel.addElement(listOfEvents.get(i));
        }
        return listModel;
    }
}
