package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.controllers.ConnectionManager;
import com.model.Movie;

public class MovieDAO {
    private Connection connection;

    public MovieDAO() {
        this.connection = ConnectionManager.getConnection();
    }

    public boolean addMovie(String name, int releaseYear, String description, String imageUrl, String type) {
        try {
            String sql = "INSERT INTO movies_tvseries (name, released_year, description, image_url, type) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setInt(2, releaseYear);
                statement.setString(3, description);
                statement.setString(4, imageUrl);
                statement.setString(5, type);

                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        try {
            String sql = "SELECT * FROM movies_tvseries";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int movieId = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int releaseYear = resultSet.getInt("released_year");
                    String description = resultSet.getString("description");
                    String imageUrl = resultSet.getString("image_url");
                    String type = resultSet.getString("type");

                    Movie movie = new Movie();
                    movie.setMovieId(movieId);
                    movie.setName(name);
                    movie.setReleasedYear(releaseYear);
                    movie.setDescription(description);
                    movie.setImageUrl(imageUrl);
                    movie.setType(type);
                    movies.add(movie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public boolean updateMovie(Movie movie) {
        try {
            String sql = "UPDATE movies_tvseries SET name = ?, released_year = ?, description = ?, image_url = ?, type = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, movie.getName());
                statement.setInt(2, movie.getReleasedYear());
                statement.setString(3, movie.getDescription());
                statement.setString(4, movie.getImageUrl());
                statement.setString(5, movie.getType());
                statement.setInt(6, movie.getMovieId());

                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMovie(int movieId) {
        try {
            String sql = "DELETE FROM movies_tvseries WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, movieId);

                int rowsDeleted = statement.executeUpdate();
                return rowsDeleted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Movie getMovieById(int movieId) {
        try {
            String sql = "SELECT * FROM movies_tvseries WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, movieId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String name = resultSet.getString("name");
                        int releaseYear = resultSet.getInt("released_year");
                        String description = resultSet.getString("description");
                        String imageUrl = resultSet.getString("image_url");
                        String type = resultSet.getString("type");

                        Movie movie = new Movie();
                        movie.setMovieId(movieId);
                        movie.setName(name);
                        movie.setReleasedYear(releaseYear);
                        movie.setDescription(description);
                        movie.setImageUrl(imageUrl);
                        movie.setType(type);
                        return movie;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
