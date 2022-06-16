package ui;

import javax.swing.*;
import java.awt.*;

// Represents the panel with the calendar title
public class TitlePanel extends JPanel {

    // MODIFIES: this
    // EFFECTS: changes the color of this panel and adds a title
    TitlePanel() {
        this.setPreferredSize(new Dimension(500, 50));
        this.setBackground(Color.darkGray);

        JLabel titleLabel = new JLabel("Calendar");
        titleLabel.setForeground(Color.lightGray);
        titleLabel.setPreferredSize(new Dimension(200, 50));
        titleLabel.setFont(new Font("Sans-serif", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(titleLabel);
    }
}
