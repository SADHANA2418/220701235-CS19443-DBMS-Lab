MainApp.java:

package com.example.demo;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class MainApp extends JFrame {

    private final Connection conn;
    private Scanner scanner;

    public MainApp() throws SQLException {
        super("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        this.scanner=scanner;

        // Establish connection to the database (replace with your credentials)
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "username", "password");

        JPanel panel = new JPanel(new GridLayout(2, 1));

        JButton loginButton = new JButton("Login");
        JButton newUserButton = new JButton("New User");
        panel.add(loginButton);
        panel.add(newUserButton);

        add(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt for email and password
                String email = JOptionPane.showInputDialog(MainApp.this, "Enter email:");
                String password = JOptionPane.showInputDialog(MainApp.this, "Enter password:");

                try {
                    int userId = UserAuth.getUserId(conn, email);

                    if (userId == -1) {
                        JOptionPane.showMessageDialog(MainApp.this, "User not found.", "Login Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (UserAuth.verifyPassword(conn, userId, password)) {
                            JOptionPane.showMessageDialog(MainApp.this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                            // Proceed with other processes after successful login
                            String name = UserAuth.getUserName(conn, userId);
                            System.out.println("Welcome back, " + name + "!");

                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    HomePage homePage = new HomePage(scanner, conn, userId);
                                    homePage.setVisible(true);
                                }
                            });
                                
                        } else {
                            JOptionPane.showMessageDialog(MainApp.this, "Incorrect password. Login failed.", "Login Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(MainApp.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        newUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newLoginUI(scanner).createNewAccount(); // Launch RegisterUI
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainApp().setVisible(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error connecting to database.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}

