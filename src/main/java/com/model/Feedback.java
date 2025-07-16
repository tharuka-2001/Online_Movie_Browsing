package com.model;

public class Feedback {
    private int feedbackId;
    private int userId;
    private String userName;
    private String comment;
    private int rate;
    private int movieId;
    // Constructors

    public Feedback() {
    }

    public Feedback(int feedbackId, int userId, String userName, String comment, int rate,int movieId) {
        this.feedbackId = feedbackId;
        this.userId = userId;
        this.userName = userName;
        this.comment = comment;
        this.rate = rate;
        this.movieId = movieId;
    }

    // Getters and Setters

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

	public void setMovieId(int movieId2) {
		this.movieId = movieId2;
		
	}
	
	public int getMovieId() {
		return this.movieId;
	}
}
