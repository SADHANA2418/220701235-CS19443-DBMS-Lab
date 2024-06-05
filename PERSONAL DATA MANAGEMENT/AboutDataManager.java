package com.example.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AboutDataManager extends JFrame {
    private final JTextField ageField;
    private final JTextField heightField;
    private final JTextField weightField;
    private final JTextField addressField;
    private final JTextField primaryPhoneField;
    private final JTextField secondaryPhoneField;
    private final Connection conn;
    private final int userId;

    public AboutDataManager(Connection conn, int userId) {
        super("About Data Manager");
        this.conn = conn;
        this.userId = userId;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(7, 2));

        JLabel ageLabel = new JLabel("Age:");
        ageField = new JTextField();
        JLabel heightLabel = new JLabel("Height (meters):");
        heightField = new JTextField();
        JLabel weightLabel = new JLabel("Weight (kg):");
        weightField = new JTextField();
        JLabel addressLabel = new JLabel("Address:");
        addressField = new JTextField();
        JLabel primaryPhoneLabel = new JLabel("Primary Phone:");
        primaryPhoneField = new JTextField();
        JLabel secondaryPhoneLabel = new JLabel("Secondary Phone:");
        secondaryPhoneField = new JTextField();
        JButton addButton = new JButton("Add");
        JButton viewButton = new JButton("View");

        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(heightLabel);
        panel.add(heightField);
        panel.add(weightLabel);
        panel.add(weightField);
        panel.add(addressLabel);
        panel.add(addressField);
        panel.add(primaryPhoneLabel);
        panel.add(primaryPhoneField);
        panel.add(secondaryPhoneLabel);
        panel.add(secondaryPhoneField);
        panel.add(addButton);
        panel.add(viewButton);

        add(panel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAboutData();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAboutData();
            }
        });
    }

    private void addAboutData() {
        try {
            int age = Integer.parseInt(ageField.getText());
            float height = Float.parseFloat(heightField.getText());
            float weight = Float.parseFloat(weightField.getText());
            String address = addressField.getText();
            String primaryPhone = primaryPhoneField.getText();
            String secondaryPhone = secondaryPhoneField.getText();

            String insertSql = "INSERT INTO about (user_id, age, height, weight, address, primary_phone, secondary_phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement insertStatement = conn.prepareStatement(insertSql)) {
                insertStatement.setInt(1, userId);
                insertStatement.setInt(2, age);
                insertStatement.setFloat(3, height);
                insertStatement.setFloat(4, weight);
                insertStatement.setString(5, address);
                insertStatement.setString(6, primaryPhone);
                insertStatement.setString(7, secondaryPhone);
                insertStatement.executeUpdate();
                JOptionPane.showMessageDialog(this, "About data added successfully.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for age, height, and weight.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewAboutData() {
        String selectSql = "SELECT * FROM about WHERE user_id = ?";
        StringBuilder aboutData = new StringBuilder();
        try (PreparedStatement selectStatement = conn.prepareStatement(selectSql)) {
            selectStatement.setInt(1, userId);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                aboutData.append("Age: ").append(resultSet.getInt("age")).append("\n");
                aboutData.append("Height: ").append(resultSet.getFloat("height")).append(" meters\n");
                aboutData.append("Weight: ").append(resultSet.getFloat("weight")).append(" kg\n");
                aboutData.append("Address: ").append(resultSet.getString("address")).append("\n");
                aboutData.append("Primary Phone: ").append(resultSet.getString("primary_phone")).append("\n");
                aboutData.append("Secondary Phone: ").append(resultSet.getString("secondary_phone")).append("\n");
                JOptionPane.showMessageDialog(this, aboutData.toString(), "About Data", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "About data not found for this user.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error retrieving about data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // You should start this UI from the HomePageUI class
    }
}
