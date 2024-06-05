package com.example.demo;


import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginUI {
    private final JFrame frame;
    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final Connection conn;
    private final Scanner scanner;

    public LoginUI(Scanner scanner) {
        this.scanner = scanner;
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(null);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 30, 80, 20);
        frame.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(120, 30, 150, 20);
        frame.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 70, 80, 20);
        frame.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(120, 70, 150, 20);
        frame.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 110, 100, 30);
        loginButton.addActionListener(e -> startLoginProcess());
        frame.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(170, 110, 100, 30);
        registerButton.addActionListener(e -> createNewAccount());
        frame.add(registerButton);
        

        conn = getConnection();
    }

    public void startLoginProcess() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        try {
            int userId = UserAuth.getUserId(conn, email);

            if (userId == -1) {
                JOptionPane.showMessageDialog(frame, "User not found.", "Login Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (UserAuth.verifyPassword(conn, userId, password)) {
                    JOptionPane.showMessageDialog(frame, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    String name = UserAuth.getUserName(conn, userId);
                    JOptionPane.showMessageDialog(frame, "Welcome back, " + name + "!", "Welcome", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Incorrect password. Login failed.", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void createNewAccount() {
        String name = JOptionPane.showInputDialog(frame, "Enter name:");
        String email = JOptionPane.showInputDialog(frame, "Enter email:");
        String password = JOptionPane.showInputDialog(frame, "Enter password:");

        try {
            int userId = UserAuth.insertUser(conn, name, email, password);
            if (userId != -1) {
                JOptionPane.showMessageDialog(frame, "Account successfully created!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to create account. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "username", "password");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Scanner scanner = new Scanner(System.in);
            LoginUI loginUI = new LoginUI(scanner);
            loginUI.show();
        });
    }
}

