package com.example.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogoutPage extends JFrame {

    public LogoutPage() {
        super("Logout");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 1));

        JLabel messageLabel = new JLabel("Are you sure you want to logout?");
        panel.add(messageLabel);

        JButton logoutButton = new JButton("Yes,Logout");
        panel.add(logoutButton);

        add(panel);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the application
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LogoutPage().setVisible(true);
            }
        });
    }
}




CREATE TABLE important_dates (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    date DATE NOT NULL,
    description VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);
CREATE TABLE email_password (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE about (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    age INT,
    height FLOAT,
    weight FLOAT,
    address VARCHAR(255),
    primary_phone VARCHAR(15),
    secondary_phone VARCHAR(15)
);
