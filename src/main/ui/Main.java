package ui;

import model.Calendar;
// creates a new instance of a GUI calendar
public class Main {

    public static void main(String[] args) {
        Calendar calendar = new Calendar("Calendar");
        new CalendarFrame(calendar);
    }
}
