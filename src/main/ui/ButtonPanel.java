package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;

import model.Calendar;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

// ButtonPanel represents a panel with buttons to add, remove, load and save events
public class ButtonPanel extends JPanel implements ActionListener {

    private CalendarView calendarView;
    private Calendar calendar;
    private JList list;
    private DefaultListModel listModel;

    private static String JSON_STORE = "./data/calendar.json";

    private JButton saveButton;
    private JButton loadButton;
    private JButton addEventButton;
    private JButton removeEventButton;
    private JLabel youAreBusy;
    private JTextField descriptionTF;
    private JTextField locationTF;
    private JTextField startTimeTF;
    private JTextField endTimeTF;
    private JTextField recurUntilTimeTF;

    // MODIFIES: this
    // EFFECTS: displays the panel and adds text fields and buttons to it.
    ButtonPanel(Calendar c, CalendarView cv, JList l, DefaultListModel lm) {
        calendar = c;
        calendarView = cv;
        list = l;
        listModel = lm;
        this.setPreferredSize(new Dimension(250, 700));
        this.setBackground(Color.lightGray);
        this.setLayout(new FlowLayout());
        addTextFieldsToPanel();

        displayAddButton();
        displayRemoveButton();
        displaySaveButton();
        displayLoadButton();
        displayYouAreBusy();

    }

    // MODIFIES: this
    // EFFECTS: displays the load button on the ButtonPanel
    private void displayLoadButton() {
        loadButton = new JButton("Load");
        loadButton.setPreferredSize(new Dimension(200, 25));
        loadButton.addActionListener(this);
        this.add(loadButton);
    }

    // MODIFIES: this
    // EFFECTS: displays the save button on the ButtonPanel
    private void displaySaveButton() {
        saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(200, 25));
        saveButton.addActionListener(this);
        this.add(saveButton);
    }

    // MODIFIES: this
    // EFFECTS: displays the remove button on the ButtonPanel
    private void displayRemoveButton() {
        removeEventButton = new JButton("Remove Event");
        removeEventButton.setPreferredSize(new Dimension(200, 25));
        removeEventButton.addActionListener(this);
        this.add(removeEventButton);
    }

    // MODIFIES: this
    // EFFECTS: displays the add button on the ButtonPanel
    private void displayAddButton() {
        addEventButton = new JButton("Add Event");
        addEventButton.setPreferredSize(new Dimension(200, 25));
        addEventButton.addActionListener(this);
        this.add(addEventButton);
    }

    // MODIFIES: this
    // EFFECTS: adds the youAreBusy image to the panel, but does not display it
    private void displayYouAreBusy() {
        try {
            BufferedImage img = ImageIO.read(new File("./data/youAreBusy.jpg"));
            Image dimg = img.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            youAreBusy = new JLabel(new ImageIcon(dimg));
            youAreBusy.setVisible(false);
            this.add(youAreBusy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: sets the visibility of youAreBusy depending on the size of the listModel
    private void setYouAreBusyVisibility() {
        if (listModel.size() < 10) {
            youAreBusy.setVisible(false);
        } else {
            youAreBusy.setVisible(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the text fields on the ButtonPanel
    private void addTextFieldsToPanel() {
        descriptionTF = new JTextField("Event Description");
        descriptionTF.setPreferredSize(new Dimension(200, 25));
        locationTF = new JTextField("Event Location");
        locationTF.setPreferredSize(new Dimension(200, 25));
        startTimeTF = new JTextField("Start Time (e.g 2007-12-03T10:15)");
        startTimeTF.setPreferredSize(new Dimension(200, 25));
        endTimeTF = new JTextField("End Time (YYYY-MM-DDTHH:MM)");
        endTimeTF.setPreferredSize(new Dimension(200, 25));
        recurUntilTimeTF = new JTextField("Recur Until (YYYY-MM-DDTHH:MM)");
        recurUntilTimeTF.setPreferredSize(new Dimension(200, 25));
        // adds Text Fields to Panel
        this.add(descriptionTF);
        this.add(locationTF);
        this.add(startTimeTF);
        this.add(endTimeTF);
        this.add(recurUntilTimeTF);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addEventButton) {
            addCalendarEvent();
        } else if (e.getSource() == removeEventButton) {
            removeCalendarEvent();
        } else if (e.getSource() == saveButton) {
            saveCalendar();
        } else if (e.getSource() == loadButton) {
            loadCalendar();
        }
    }

    // MODIFIES: this
    // EFFECTS: removes an event from the calendar at a selected index
    private void removeCalendarEvent() {
        try {
            int index = list.getSelectedIndex();
            Event event = (Event)listModel.get(index);
            calendar.removeEvent(event.getEventId());
            listModel.remove(index);
            setYouAreBusyVisibility();
        } catch (Exception e) {
            alert("Unable to remove..." + e.getMessage());
        }
    }

    // MODIFIES: this
    //EFFECTS: adds an event from the calendar based on text fields
    private void addCalendarEvent() {
        try {
            String description = descriptionTF.getText();
            String location = locationTF.getText();
            LocalDateTime startDateTime = LocalDateTime.parse(startTimeTF.getText());
            LocalDateTime endDateTime = LocalDateTime.parse(endTimeTF.getText());
            LocalDateTime recurringUntil = LocalDateTime.parse(recurUntilTimeTF.getText());
            Event event = new Event(description, location, startDateTime, endDateTime, recurringUntil);
            calendar.addEvent(event);
            listModel = calendarView.getCalendarForList(calendar);
            setYouAreBusyVisibility();
        } catch (Exception e) {
            alert("Unable to add..." + e.getMessage());
        }
    }

    // EFFECTS: saves the calendar to file
    private void saveCalendar() {

        try {
            JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
            jsonWriter.open();
            jsonWriter.write(calendar);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            alert("Unable to write to file: " + JSON_STORE);
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Calendar from file
    private void loadCalendar() {
        try {
            JsonReader jsonReader = new JsonReader(JSON_STORE);
            calendar = jsonReader.read();
            listModel = calendarView.getCalendarForList(calendar);
            setYouAreBusyVisibility();
        } catch (IOException e) {
            alert("Unable to load from file: " + JSON_STORE);
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: displays pop up message
    private void alert(String str) {
        JOptionPane.showMessageDialog(null, str);
    }

}
