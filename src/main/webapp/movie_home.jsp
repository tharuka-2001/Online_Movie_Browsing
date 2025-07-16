<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.model.Package" %>
<%@ page import="com.model.Movie" %>
<%@ page import="com.model.Feedback" %>
<%@ page import="java.util.List" %>
<% 
    List<Package> packages = (List<Package>) request.getAttribute("packages");
    List<Feedback> feedbacks = (List<Feedback>) request.getAttribute("feedbacks");
    List<Movie> movies = (List<Movie>) request.getAttribute("movies");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie Land</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #121212; /* Dark background color */
            color: #fff; /* Light text color */
        }

        .navbar {
            background-color: #000; /* Black navbar background color */
            padding: 10px;
            text-align: center;
            display:flex;
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
            cursor:pointer;
        }

        .movies-container {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
            grid-gap: 20px;
            padding: 20px;
        }

        .movie-card, .package-card {
            background-color: #333; /* Dark card background color */
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
            transition: transform 0.3s;
            cursor: pointer;
        }

        .movie-card:hover, .package-card:hover {
            transform: scale(1.05);
        }

        .movie-image, .package-image {
            width: 100%;
            height: 200px;
            object-fit: cover;
        }

        .movie-details, .package-details {
            padding: 10px;
        }

        .movie-title, .package-title {
            font-size: 18px;
            font-weight: bold;
        }

        .movie-description, .package-description {
            font-size: 14px;
        }

        /* Modal Styles */
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.8);
            padding-top: 60px;
        }

        .modal-content {
            background-color: #333;
            margin: 5% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: #fff;
            text-decoration: none;
            cursor: pointer;
        }

        form {
            margin-top: 20px;
            display: flex;
            flex-direction: column;
        }

        label {
            margin-bottom: 10px;
            color: #fff;
        }

        input, textarea, button {
            margin-bottom: 15px;
            padding: 10px;
        }

        button {
            background-color: #4CAF50;
            color: #fff;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div class="navbar">
        <a href="#" class="navbar-title">Movie Land</a>
        <img src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png" alt="User Avatar" class="avatar" onclick="location.href='user?action=edit';">
    </div>

    <h1 style="color:white">Movies</h1>
    <div class="movies-container">
        <%-- Display Movies --%>
        <% 
            if(movies != null) {
                for(Movie movie : movies) {
        %>
                    <div class="movie-card" onclick="openFeedbackModal('<%= movie.getName() %>')">
                        <img src="<%= movie.getImageUrl() %>" alt="Movie" class="movie-image">
                        <div class="movie-details">
                            <div class="movie-title"><%= movie.getName() %></div>
                            <div class="movie-description"><%= movie.getDescription() %></div>
                            <div class="movie-description"><%= movie.getReleasedYear() %></div>
                        </div>
                    </div>
        <% 
                }
            }
        %>
    </div>



    <h1 style="color:white">Packages</h1>
    <div class="movies-container">
        <%-- Display Movies --%>
        <% 
            if(packages != null) {
                for(Package pkg : packages) {
        %>
                       
                       <div class="movie-card">
                        <div class="movie-details">
                            <div class="movie-title"><%= pkg.getPackageName() %></div>
                            <div class="movie-description"><%= pkg.getDescription() %></div>
                            <div class="movie-description">LKR <%= pkg.getPrice() %></div>
                        </div>
                       </div>
                    
        <% 
                }
            }
        %>
    </div>

    <!-- Feedback Modal -->
    <div id="feedbackModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeFeedbackModal()">&times;</span>
            <h2>Add Feedback for <span id="movieTitle"></span></h2>
            <form action="feedback?action=add" method="post">
                <input type="hidden" name="movieId" id="movieId" value="">
                
                <label for="userName">User Name:</label>
                <input type="text" name="userName" required>

                <label for="comment">Comment:</label>
                <textarea name="comment" rows="4" required></textarea>

                <label for="rate">Rate:</label>
                <input type="number" name="rate" min="1" max="5" required>

                <button type="submit">Submit Feedback</button>
            </form>
        </div>
    </div>

    <script>
        function openFeedbackModal(movieTitle) {
            var movieId = getMovieId(movieTitle);
            document.getElementById('movieTitle').innerHTML = movieTitle;
            document.getElementById('movieId').value = movieId;
            document.getElementById('feedbackModal').style.display = 'block';
        }

        function closeFeedbackModal() {
            document.getElementById('feedbackModal').style.display = 'none';
        }

        function getMovieId(movieTitle) {
            // Implement logic to get the movieId based on the movieTitle
            // This can be done using JavaScript or by fetching data from the server
            // For simplicity, I'm returning a hardcoded value here.
            return 1; // Replace with actual movieId
        }
    </script>
</body>
</html>
