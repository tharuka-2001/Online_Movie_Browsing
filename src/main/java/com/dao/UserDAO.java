package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.controllers.ConnectionManager;
import com.model.User;

public class UserDAO {
    private Connection connection;

    public UserDAO() {
        this.connection = ConnectionManager.getConnection();
    }

    public boolean addUser(String userName, String email, String password, String contactNumber) {
        try {
            String sql = "INSERT INTO users (user_name, email, password, contact_number) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, userName);
                statement.setString(2, email);
                statement.setString(3, password);
                statement.setString(4, contactNumber);

                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean loginUser(String email, String password) {
        try {
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                statement.setString(2, password);

                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next(); // Returns true if user with given email and password exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            String sql = "SELECT * FROM users";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int userId = resultSet.getInt("user_id");
                    String userName = resultSet.getString("user_name");
                    String email = resultSet.getString("email");
                    String contactNumber = resultSet.getString("contact_number");

                    User user = new User();
                    user.setUserId(userId);
                    user.setContactNumber(contactNumber);
                    user.setEmail(email);
                    user.setUserName(userName);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean updateUser(User user) {
        try {
            String sql = "UPDATE users SET user_name = ?, email = ?,  contact_number = ? WHERE user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, user.getUserName());
                statement.setString(2, user.getEmail());
               
                statement.setString(3, user.getContactNumber());
                statement.setInt(4, user.getUserId());

                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteUser(int userId) {
        try {
            String sql = "DELETE FROM users WHERE user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, userId);

                int rowsDeleted = statement.executeUpdate();
                return rowsDeleted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public User getUserById(int userId) {
        try {
            String sql = "SELECT * FROM users WHERE user_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, userId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String userName = resultSet.getString("user_name");
                        String email = resultSet.getString("email");
                        String contactNumber = resultSet.getString("contact_number");

                        User user = new User();
                        user.setUserId(userId);
                        user.setContactNumber(contactNumber);
                        user.setEmail(email);
                        user.setUserName(userName);
                        return user;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public User getUserByEmail(String userId) {
        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, userId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                    	 int id = Integer.parseInt(resultSet.getString("user_id"));
                        String userName = resultSet.getString("user_name");
                        String email = resultSet.getString("email");
                        String contactNumber = resultSet.getString("contact_number");

                        User user = new User();
                        user.setUserId(id);
                        user.setContactNumber(contactNumber);
                        user.setEmail(email);
                        user.setUserName(userName);
                        return user;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
   
}
