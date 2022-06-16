package ui;

import model.Calendar;
import model.Incident;
import model.IncidentLog;

import javax.swing.*;

import java.awt.*;
import java.util.Iterator;

// Displays a GUI calendar
public class CalendarFrame extends JFrame {

    private CalendarView calendarView;
    private Calendar calendar;
    private JList list;
    private DefaultListModel listModel;

    // MODIFIES: this
    // EFFECTS: Displays the calendarFrame and all panels within the frame
    CalendarFrame(Calendar c) {
        calendar = c;
        this.setSize(800, 800);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                Iterator<Incident> it = IncidentLog.getInstance().iterator();
                while (it.hasNext()) {
                    Incident i = it.next();
                    System.out.print(i.toString());
                }

                System.exit(0);
            }
        });
        this.add(new TitlePanel(), BorderLayout.NORTH);
        calendarView = new CalendarView();
        listModel = calendarView.getCalendarForList(calendar);
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        JScrollPane listScrollPane = new JScrollPane(list);
        this.add(listScrollPane, BorderLayout.CENTER);
        this.add(new ButtonPanel(calendar, calendarView, list, listModel), BorderLayout.EAST);
        this.setVisible(true);
    }

}