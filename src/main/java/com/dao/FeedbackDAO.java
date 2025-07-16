package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.controllers.ConnectionManager;
import com.model.Feedback;

public class FeedbackDAO {
    private Connection connection;

    public FeedbackDAO() {
        this.connection = ConnectionManager.getConnection();
    }

    public boolean addFeedback(int userId, String userName, String comment, int rate, int movieId) {
        try {
            String sql = "INSERT INTO feedback (user_id, user_name, comment, rate, movie_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, 1);
                statement.setString(2, userName);
                statement.setString(3, comment);
                statement.setInt(4, rate);
                statement.setInt(5, movieId);

                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Feedback> getAllFeedback() {
        List<Feedback> feedbackList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM feedback";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int feedbackId = resultSet.getInt("feedback_id");
                    int userId = resultSet.getInt("user_id");
                    String userName = resultSet.getString("user_name");
                    String comment = resultSet.getString("comment");
                    int rate = resultSet.getInt("rate");
                    int movieId = resultSet.getInt("movie_id");

                    Feedback feedback = new Feedback();
                    feedback.setFeedbackId(feedbackId);
                    feedback.setUserId(userId);
                    feedback.setUserName(userName);
                    feedback.setComment(comment);
                    feedback.setRate(rate);
                    feedback.setMovieId(movieId);

                    feedbackList.add(feedback);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

    public boolean updateFeedback(Feedback feedback) {
        try {
            String sql = "UPDATE feedback SET user_id = ?, user_name = ?, comment = ?, rate = ?, movie_id = ? WHERE feedback_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, feedback.getUserId());
                statement.setString(2, feedback.getUserName());
                statement.setString(3, feedback.getComment());
                statement.setInt(4, feedback.getRate());
                statement.setInt(5, feedback.getMovieId());
                statement.setInt(6, feedback.getFeedbackId());
           
                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFeedback(int feedbackId) {
        try {
            String sql = "DELETE FROM feedback WHERE feedback_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, feedbackId);

                int rowsDeleted = statement.executeUpdate();
                return rowsDeleted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Feedback getFeedbackById(int feedbackId) {
        try {
            String sql = "SELECT * FROM feedback WHERE feedback_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, feedbackId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int userId = resultSet.getInt("user_id");
                        String userName = resultSet.getString("user_name");
                        String comment = resultSet.getString("comment");
                        int rate = resultSet.getInt("rate");
                        int movieId = resultSet.getInt("movie_id");

                        Feedback feedback = new Feedback();
                        feedback.setFeedbackId(feedbackId);
                        feedback.setUserId(userId);
                        feedback.setUserName(userName);
                        feedback.setComment(comment);
                        feedback.setRate(rate);
                        feedback.setMovieId(movieId);

                        return feedback;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public List<Feedback> getFeedbacksByMovieId(int movieId) {
        List<Feedback> feedbackList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM feedback WHERE movie_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, movieId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int feedbackId = resultSet.getInt("feedback_id");
                        int userId = resultSet.getInt("user_id");
                        String userName = resultSet.getString("user_name");
                        String comment = resultSet.getString("comment");
                        int rate = resultSet.getInt("rate");

                        Feedback feedback = new Feedback();
                        feedback.setFeedbackId(feedbackId);
                        feedback.setUserId(userId);
                        feedback.setUserName(userName);
                        feedback.setComment(comment);
                        feedback.setRate(rate);

                        feedbackList.add(feedback);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbackList;
    }

}
