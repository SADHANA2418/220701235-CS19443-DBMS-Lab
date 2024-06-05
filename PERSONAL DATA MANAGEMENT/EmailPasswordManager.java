package com.example.demo;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class EmailPasswordManager extends JFrame {
    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final int userId;

    public EmailPasswordManager(Connection conn, int userId) {
        super("Email Password Manager");
        this.userId = userId;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JButton addButton = new JButton("Add");
        JButton viewButton = new JButton("View");

        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(addButton);
        panel.add(viewButton);

        add(panel);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmailPassword(conn);
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewEmailPasswords(conn);
            }
        });
    }

    private void addEmailPassword(Connection conn) {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        String insertSql = "INSERT INTO email_passwords (user_id, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement insertStatement = conn.prepareStatement(insertSql)) {
            insertStatement.setInt(1, userId);
            insertStatement.setString(2, email);
            insertStatement.setString(3, password);
            insertStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Email ID and password stored successfully.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewEmailPasswords(Connection conn) {
        String selectSql = "SELECT email, password FROM email_passwords WHERE user_id = ?";
        StringBuilder emailPasswordPairs = new StringBuilder();
        try (PreparedStatement selectStatement = conn.prepareStatement(selectSql)) {
            selectStatement.setInt(1, userId);
            ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                emailPasswordPairs.append("Email: ").append(resultSet.getString("email")).append(", Password: ").append(resultSet.getString("password")).append("\n");
            }
            JOptionPane.showMessageDialog(this, emailPasswordPairs.toString(), "Email-Password Pairs", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error retrieving email-password pairs: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // You should start this UI from the HomePageUI class
    }
}
