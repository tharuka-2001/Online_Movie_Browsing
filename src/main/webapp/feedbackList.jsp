<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.model.Feedback" %>
<%@ page import="com.model.Movie" %>
<%@ page import="java.util.List" %>
<%@ page import="com.dao.FeedbackDAO" %>
<%@ page import="com.dao.MovieDAO" %>
<%@ page import="java.util.Iterator" %>

<%
    // Fetch movies from the database
    MovieDAO movieDAO = new MovieDAO();
    List<Movie> movies = movieDAO.getAllMovies();

    // Fetch feedback list from the database
    FeedbackDAO feedbackDAO = new FeedbackDAO();
    List<Feedback> feedbackList = feedbackDAO.getAllFeedback();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Feedback List</title>
    <style>
 @import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css');
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body {
    font-family: 'Arial', sans-serif;
    background-color: #121212; /* Dark background color */
    color: #fff; /* Light text color */
    margin:32px;
    padding: 32px;
}

.navbar {
    background-color: #000; /* Black navbar background color */
    padding: 10px;
    text-align: center;
    display: flex;
}

.navbar-title {
    font-size: 24px;
    font-weight: bold;
    color: #fff; /* White text color */
    text-decoration: none;
}

.avatar {
    margin-left: auto;
    margin-right: 20px;
    border-radius: 50%;
    width: 40px;
    height: 40px;
    cursor: pointer;
}

h1, h2 {
    color: white;
}

.movies-container {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
    gap: 20px;
    padding: 20px;
}

.feedback-card {
    background-color: #333; /* Dark card background color */
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
    transition: transform 0.3s;
    display: flex;
    flex-direction: column;
}

.feedback-card:hover {
    transform: scale(1.05);
}

.feedback-details {
    padding: 10px;
    flex-grow: 1;
}

.feedback-title {
    font-size: 18px;
    font-weight: bold;
}

.feedback-comment, .feedback-rate {
    font-size: 14px;
    margin-top: 5px;
}

.feedback-actions {
    padding: 10px;
    text-align: center;
    background-color: #000;
}

.feedback-actions a {
    color: #fff;
    text-decoration: none;
    margin: 0 10px;
}

.feedback-actions a:hover {
    text-decoration: underline;
}

form {
    display: flex;
    flex-direction: column;
    width: 300px;
    margin: 20px auto;
}

label {
    margin-top: 10px;
    color: #fff;
}

select, input, textarea {
    margin-top: 5px;
    padding: 8px;
    border: 1px solid #555;
    border-radius: 4px;
}

button {
    margin-top: 10px;
    padding: 10px;
    background-color: #000;
    color: #fff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

button:hover {
    background-color: #333;
}
    .star {
        color: #ffd700; /* Set star color to gold */
        cursor: pointer;
        transition: color 0.3s;
    }

    .star:hover {
        color: #ffcc00; /* Change color on hover */
    }
    </style>
</head>
<body>
    <div class="navbar">
        <a href="movie" class="navbar-title">Movie Land</a>
        <img src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png" alt="User Avatar" class="avatar" onclick="location.href='user?action=edit';">
    </div>

    <h1 style="color: white;">Feedback List</h1>

    <div class="movies-container">
        <%-- Feedback List --%>
        <%
            if(feedbackList!=null){for (Feedback feedback : feedbackList) {
        %>
            <div class="feedback-card">
                <div class="feedback-details">
                    <div class="feedback-title">User: <%= feedback.getUserName() %></div>
                    <div class="feedback-comment"><%= feedback.getComment() %></div>
                    <div class="feedback-rate">Rate: <%= feedback.getRate() %></div>
                </div>
                <div class="feedback-actions">
                     <a href="javascript:void(0);" 
                     onclick
                     ="
                     openEditModal(
                     	<%= feedback.getFeedbackId() %>, 
                     	'<%= feedback.getComment() %>', 
                     	<%= feedback.getRate() %>,
                     	<%= feedback.getMovieId() %>,
                     	'<%= feedback.getUserName() %>')">Edit</a>
                    <a href="feedback?action=delete&feedbackId=<%= feedback.getFeedbackId() %>">Delete</a>
                </div>
            </div>
        <%
            }}
        %>
    </div>

    <div id="editFeedbackModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeEditModal()">&times;</span>
        <h2>Edit Feedback</h2>
        <form action="feedback?action=update" method="post">
            <input type="hidden"  id="editFeedbackId" name="feedbackId" value="">
             <input type="hidden" id="editMovieId" name="movieId" value="">
                <input type="hidden" id="editusername" name=userName value="">
            <label for="editComment">Comment:</label>
            <textarea id="editComment" name="comment" rows="4" required></textarea>

           <label for="editRate">Rate:</label>
         	  <input type="hidden" id="editRate" name="rate" value="">
            <div>
                <span class="star" onclick="rateFeedback(1)"><i class="fas fa-star"></i></span>
                <span class="star" onclick="rateFeedback(2)"><i class="fas fa-star"></i></span>
                <span class="star" onclick="rateFeedback(3)"><i class="fas fa-star"></i></span>
                <span class="star" onclick="rateFeedback(4)"><i class="fas fa-star"></i></span>
                <span class="star" onclick="rateFeedback(5)"><i class="fas fa-star"></i></span>
            </div>

            <button type="submit">Save Changes</button>
        </form>
    </div>
</div>
<script>
    function openEditModal(feedbackId, comment, rate,movieId,username) {
    	document.getElementById('editFeedbackId').value = feedbackId;
        document.getElementById('editMovieId').value = movieId;
        document.getElementById('editusername').value = username;
        document.getElementById('editComment').value = comment;
        setStarRating(rate);
        document.getElementById('editFeedbackModal').style.display = 'block';
    }
    function setStarRating(rate) {
        const stars = document.querySelectorAll('.star');
        stars.forEach((star, index) => {
            if (index < rate) {
                star.style.color = '#ffd700'; // Set color to gold for selected stars
            } else {
                star.style.color = '#ccc'; // Set color to gray for unselected stars
            }
        });
    }
    function rateFeedback(rating) {
        setStarRating(rating);
        document.getElementById('editRate').value = rating;
    }

    function closeEditModal() {
        document.getElementById('editFeedbackModal').style.display = 'none';
    }
</script>
</body>
</html>
