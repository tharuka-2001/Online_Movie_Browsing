<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.model.Movie" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Movie Management</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-4">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="adminHome.jsp">Admin Dashboard</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav"
        aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
   
</nav>
    <h2>Movie Management</h2>

    <!-- Display success or error messages -->

    <!-- Display all movies -->
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Movie ID</th>
                <th>Name</th>
                <th>Release Year</th>
                <th>Description</th>
                <th>Type</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
            List<Movie> movies = (List<Movie>) request.getAttribute("movies");
            if (movies != null) {
                for (Movie movie : movies) { %>
                    <tr>
                        <td><%= movie.getMovieId() %></td>
                        <td><%= movie.getName() %></td>
                        <td><%= movie.getReleasedYear() %></td>
                        <td><%= movie.getDescription() %></td>
                        <td><%= movie.getType() %></td>
                        <td>
                            <!-- Edit button with data-movie-id attribute -->
                            <a href="#" class="btn btn-primary editMovieLink" data-movie-id="<%= movie.getMovieId() %>">Edit</a>
                            <!-- Delete button with data-movie-id attribute -->
                            <a href="#" class="btn btn-danger deleteMovieLink" data-movie-id="<%= movie.getMovieId() %>">Delete</a>
                        </td>
                    </tr>
                <%} } %>
        </tbody>
    </table>
    <!-- Add a button to create a new movie -->
    <button class="btn btn-success" id="addMovieBtn">Add Movie</button>

</div>

<!-- Add Movie Modal -->
<div class="modal fade" id="addMovieModal" tabindex="-1" role="dialog" aria-labelledby="addMovieModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addMovieModalLabel">Add Movie</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <!-- Add Movie Form with client-side validation -->
                <form action="<%= request.getContextPath() %>/movie?action=add" method="post" id="addMovieForm">
                    <div class="form-group">
                        <label for="addMovieName">Name:</label>
                        <input type="text" class="form-control" id="addMovieName" name="name" required>
                    </div>
                     <div class="form-group">
                        <label for="imageUrl">Image Url :</label>
                        <input type="text" class="form-control" id="imageUrl" name="imageUrl" required>
                    </div>
                    <div class="form-group">
                        <label for="addReleaseYear">Release Year:</label>
                        <input type="number" class="form-control" id="addReleaseYear" name="releaseYear" required>
                    </div>
                    <div class="form-group">
                        <label for="addDescription">Description:</label>
                        <textarea class="form-control" id="addDescription" name="description" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="addType">Type:</label>
                        <!-- Use a dropdown for movie type -->
                        <select class="form-control" id="addType" name="type" required>
                            <option value="movie">Movie</option>
                            <option value="tvseries">Tv Sereies</option>
                           
                            <!-- Add other options as needed -->
                        </select>
                    </div>
                    <!-- Add other form fields as needed -->
                    <button type="submit" class="btn btn-primary">Add Movie</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Edit Movie Modal -->
<div class="modal fade" id="editMovieModal" tabindex="-1" role="dialog" aria-labelledby="editMovieModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editMovieModalLabel">Edit Movie</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <!-- Edit Movie Form with client-side validation -->
                <form action="<%= request.getContextPath() %>/movie?action=update" method="post" id="editMovieForm">
                	   <input type="text" class="form-control" id="editMovieId" style="display:none" name="movieId" required>
                    <div class="form-group">
                        <label for="editMovieName">Name:</label>
                        <input type="text" class="form-control" id="editMovieName" name="name" required>
                    </div>
                    <div class="form-group">
                        <label for="editReleaseYear">Release Year:</label>
                        <input type="number" class="form-control" id="editReleaseYear" name="releaseYear" required>
                    </div>
                       <div class="form-group">
                        <label for="editImageUrl">Image Url :</label>
                        <input type="text" class="form-control" id="editImageUrl" name="imageUrl" required>
                    </div>
                    <div class="form-group">
                        <label for="editDescription">Description:</label>
                        <textarea class="form-control" id="editDescription" name="description" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="editType">Type:</label>
                        <!-- Use a dropdown for movie type -->
                        <select class="form-control" id="editType" name="type" required>
                            <option value="movie">Movie</option>
                            <option value="tvseries">Tv Series</option>
                       
                            <!-- Add other options as needed -->
                        </select>
                    </div>
                    <!-- Add other form fields as needed -->
                    <button type="submit" class="btn btn-primary">Save Changes</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Delete Confirmation Modal -->
<div class="modal fade" id="deleteConfirmationModal" tabindex="-1" role="dialog" aria-labelledby="deleteConfirmationModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteConfirmationModalLabel">Confirm Deletion</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Are you sure you want to delete this movie?
            </div>
            <div class="modal-footer">
                <a href="#" id="deleteMovieLink" class="btn btn-danger">Delete</a>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script>
    $(document).ready(function() {
        // Show add movie modal on button click
        $('#addMovieBtn').click(function() {
            $('#addMovieModal').modal('show');
        });

        // Show edit movie modal on edit button click
        $('.editMovieLink').click(function() {
            var movieId = $(this).data('movie-id');
            // Load movie data using AJAX and populate the edit modal
            loadEditData(movieId);
        });

        // Show delete confirmation modal on delete button click
        $('.deleteMovieLink').click(function() {
            var movieId = $(this).data('movie-id');
            // Set the delete link href dynamically based on movieId
            $('#deleteMovieLink').attr('href', '<%= request.getContextPath() %>/movie?action=delete&movieId=' + movieId);
            $('#deleteConfirmationModal').modal('show');
        });

        // Client-side validation for Add Movie Form
        $('#addMovieForm').submit(function(event) {
            var isValid = true;
            // Example: Add validation logic for each field
            if ($('#addMovieName').val().trim() === '') {
                alert('Movie Name is required.');
                isValid = false;
            }
            // Add other validation rules as needed

            if (!isValid) {
                event.preventDefault(); // Prevent form submission
            }
        });

        // Client-side validation for Edit Movie Form
        $('#editMovieForm').submit(function(event) {
            var isValid = true;
            // Example: Add validation logic for each field
            if ($('#editMovieName').val().trim() === '') {
                alert('Movie Name is required.');
                isValid = false;
            }
            

            if (!isValid) {
                event.preventDefault(); // Prevent form submission
            }
        });

        function loadEditData(movieId) {
            // Make an AJAX request to fetch data for the selected movie
            fetch('<%= request.getContextPath() %>/movie?action=edit&movieId=' + movieId, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .then(response => response.json())
            .then(data => {
              	$('#editMovieId').val(data.movieId)
                $('#editMovieName').val(data.name);
                $('#editReleaseYear').val(data.releasedYear);
                $('#editDescription').val(data.description);
                $('#editType').val(data.type);
				$('#editImageUrl').val(data.imageUrl);
                // Show the edit modal
                $('#editMovieModal').modal('show');
            })
            .catch(error => {
                // Handle errors
                console.error('Failed to fetch data for movie ID: ' + movieId);
            });
        }
    });
</script>

</body>
</html>
