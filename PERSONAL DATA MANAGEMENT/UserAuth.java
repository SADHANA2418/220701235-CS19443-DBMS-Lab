package com.example.demo;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAuth {

    public static int getUserId(Connection conn, String email) throws SQLException {
        String selectSql = "SELECT id FROM users WHERE email = ?";
        try (PreparedStatement selectStatement = conn.prepareStatement(selectSql)) {
            selectStatement.setString(1, email);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                return -1;
            }
        }
    }

    public static int insertUser(Connection conn, String name, String email, String password) throws SQLException {
        String insertSql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement insertStatement = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStatement.setString(1, name);
            insertStatement.setString(2, email);
            insertStatement.setString(3, password);
            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Failed to get auto-generated user ID.");
            }
        }
    }

    public static boolean verifyPassword(Connection conn, int userId, String password) throws SQLException {
        String selectSql = "SELECT password FROM users WHERE id = ?";
        try (PreparedStatement selectStatement = conn.prepareStatement(selectSql)) {
            selectStatement.setInt(1, userId);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                return password.equals(resultSet.getString("password"));
            } else {
                throw new SQLException("User with ID " + userId + " not found.");
            }
        }
    }

    public static String getUserName(Connection conn, int userId) throws SQLException {
        String selectSql = "SELECT name FROM users WHERE id = ?";
        try (PreparedStatement selectStatement = conn.prepareStatement(selectSql)) {
            selectStatement.setInt(1, userId);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("name");
            } else {
                throw new SQLException("User with ID " + userId + " not found.");
            }
        }
    }

