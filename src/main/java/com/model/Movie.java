package com.model;

public class Movie {
    private int movieId;
    private String name;
    private int releasedYear;
    private String description;
    private String imageUrl;
    private String type;

    // Constructors

    public Movie() {
    }

    public Movie(int movieId, String name, int releasedYear, String description, String imageUrl, String type) {
        this.movieId = movieId;
        this.name = name;
        this.releasedYear = releasedYear;
        this.description = description;
        this.imageUrl = imageUrl;
        this.type = type;
    }

    // Getters and Setters

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReleasedYear() {
        return releasedYear;
    }

    public void setReleasedYear(int releasedYear) {
        this.releasedYear = releasedYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
