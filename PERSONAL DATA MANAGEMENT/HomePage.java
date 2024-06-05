package com.example.demo;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class HomePage extends JFrame {
    private final Scanner scanner;
    private final Connection conn;
    private final int userId;

    public HomePage(Scanner scanner, Connection conn, int userId) {
        super("Home Page");
        this.scanner = scanner;
        this.conn = conn;
        this.userId = userId;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 1));

        JButton emailPasswordButton = new JButton("Email-Password Management");
        JButton importantDatesButton = new JButton("Important Dates Management");
        JButton aboutDataButton = new JButton("About Data Management");
        JButton emergencyHelplineButton = new JButton("Emergency Helpline Numbers");
        JButton logoutPageButton = new JButton("LOGOUT");

        panel.add(emailPasswordButton);
        panel.add(importantDatesButton);
        panel.add(aboutDataButton);
        panel.add(emergencyHelplineButton);
        panel.add(logoutPageButton);

        add(panel);

        emailPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEmailPasswordManager();
            }
        });

        importantDatesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openImportantDateManager();
            }
        });

        aboutDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAboutDataManager();
            }
        });
        
        emergencyHelplineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openEmergencyHelplineManager();
            }
        });
        
        logoutPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLogoutPage();
            }
        });
    }

    private void openEmailPasswordManager() {
        // Instantiate and display the EmailPasswordManagerUI
        EmailPasswordManager emailPasswordManagerUI = new EmailPasswordManager(conn, userId);
        emailPasswordManagerUI.setVisible(true);
    }

    private void openImportantDateManager() {
        // Instantiate and display the ImportantDateManagerUI
        ImportantDateManager importantDateManagerUI = new ImportantDateManager(conn, userId);
        importantDateManagerUI.setVisible(true);
    }

    private void openAboutDataManager() {
        // Instantiate and display the AboutDataManagerUI
        AboutDataManager aboutDataManagerUI = new AboutDataManager(conn, userId);
        aboutDataManagerUI.setVisible(true);
    }
    
    private void openEmergencyHelplineManager() {
        // Instantiate and display the AboutDataManagerUI
    	EmergencyHelplineManager emergencyHelpline = new EmergencyHelplineManager();
        emergencyHelpline.setVisible(true);
    }
    
    private void openLogoutPage() {
        // Instantiate and display the AboutDataManagerUI
    	LogoutPage logoutPage = new LogoutPage();
        logoutPage.setVisible(true);
    }



    public static void main(String[] args) {
        // This class should not be run directly as a Swing application
    }
}}

