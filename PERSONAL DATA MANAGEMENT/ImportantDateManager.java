package com.example.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImportantDateManager extends JFrame {
    private final JTextField dateField;
    private final JTextField descriptionField;
    private final Connection conn;
    private final int userId;

    public ImportantDateManager(Connection conn, int userId) {
        super("Important Date Manager");
        this.conn = conn;
        this.userId = userId;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        dateField = new JTextField();
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField();
        JButton addButton = new JButton("Add");
        JButton viewButton = new JButton("View");

        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(descriptionLabel);
        panel.add(descriptionField);
        panel.add(addButton);
        panel.add(viewButton);

        add(panel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addImportantDate();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewImportantDates();
            }
        });
    }

    private void addImportantDate() {
        String date = dateField.getText();
        String description = descriptionField.getText();

        String insertSql = "INSERT INTO important_dates (user_id, date, description) VALUES (?, ?, ?)";
        try (PreparedStatement insertStatement = conn.prepareStatement(insertSql)) {
            insertStatement.setInt(1, userId);
            insertStatement.setString(2, date);
            insertStatement.setString(3, description);
            insertStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Date and description stored successfully.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewImportantDates() {
        String selectSql = "SELECT date, description FROM important_dates WHERE user_id = ?";
        StringBuilder dates = new StringBuilder();
        try (PreparedStatement selectStatement = conn.prepareStatement(selectSql)) {
            selectStatement.setInt(1, userId);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                dates.append("Date: ").append(resultSet.getString("date")).append(", Description: ").append(resultSet.getString("description")).append("\n");
            }
            JOptionPane.showMessageDialog(this, dates.toString(), "Important Dates", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error retrieving important dates: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // You should start this UI from the HomePageUI class
    }
}
