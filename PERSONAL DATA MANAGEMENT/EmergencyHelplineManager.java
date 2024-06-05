package com.example.demo;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class EmergencyHelplineManager extends JFrame {
    public EmergencyHelplineManager() {
        super("Emergency Helpline Numbers");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JTextArea helplineNumbersArea = new JTextArea();
        helplineNumbersArea.setEditable(false);
        helplineNumbersArea.setLineWrap(true);
        helplineNumbersArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(helplineNumbersArea);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        displayEmergencyHelplines(helplineNumbersArea);
    }

    private void displayEmergencyHelplines(JTextArea helplineNumbersArea) {
        StringBuilder helplineNumbers = new StringBuilder();
        helplineNumbers.append("Emergency Helpline Numbers:\n");
        helplineNumbers.append("1. NATIONAL EMERGENCY NUMBER: 112\n");
        helplineNumbers.append("2. POLICE: 100 or 112\n");
        helplineNumbers.append("3. FIRE: 101\n");
        helplineNumbers.append("4. AMBULANCE: 102\n");
        helplineNumbers.append("5. Disaster Management Services: 108\n");
        helplineNumbers.append("6. Women Helpline: 1091\n");
        helplineNumbers.append("7. Women Helpline - (Domestic Abuse): 181");

        helplineNumbersArea.setText(helplineNumbers.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EmergencyHelplineManager().setVisible(true);
            }
        });
    }
}

