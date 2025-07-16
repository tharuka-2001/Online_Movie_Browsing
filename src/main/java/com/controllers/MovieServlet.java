package com.controllers;

import com.dao.FeedbackDAO;
import com.dao.MovieDAO;
import com.dao.PackageDAO;
import com.model.Feedback;
import com.model.Movie;
import com.model.Package;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class MovieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private MovieDAO movieDAO = new MovieDAO();
	private FeedbackDAO feedBackDao =new FeedbackDAO();
	private PackageDAO packageDao =new PackageDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    addMovie(request, response);
                    break;
                case "update":
                    updateMovie(request, response);
                    break;
                case "delete":
                    deleteMovie(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/error.jsp");
            }
        }
    }

    private void addMovie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get movie data from the form
        String name = request.getParameter("name");
        int releaseYear = Integer.parseInt(request.getParameter("releaseYear"));
        String description = request.getParameter("description");
        String imageUrl = request.getParameter("imageUrl");
        String type = request.getParameter("type");

        // Create a new movie
        Movie movie = new Movie();
        movie.setName(name);
        movie.setReleasedYear(releaseYear);
        movie.setDescription(description);
        movie.setImageUrl(imageUrl);
        movie.setType(type);

        // Add the movie to the database
        boolean success = movieDAO.addMovie( name,  releaseYear,  description,  imageUrl,  type);

        // Redirect to appropriate page based on the result
        if (success) {
            response.sendRedirect(request.getContextPath() + "/movie?action=admin");
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    private void updateMovie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get movie data from the form
        int movieId = Integer.parseInt(request.getParameter("movieId"));
        String name = request.getParameter("name");
        int releaseYear = Integer.parseInt(request.getParameter("releaseYear"));
        String description = request.getParameter("description");
        String imageUrl = request.getParameter("imageUrl");
        String type = request.getParameter("type");

        // Create a movie object with updated data
        Movie updatedMovie = new Movie();
        updatedMovie.setMovieId(movieId);
        updatedMovie.setName(name);
        updatedMovie.setReleasedYear(releaseYear);
        updatedMovie.setDescription(description);
        updatedMovie.setImageUrl(imageUrl);
        updatedMovie.setType(type);

        // Update the movie in the database
        boolean success = movieDAO.updateMovie(updatedMovie);

        // Redirect to appropriate page based on the result
        if (success) {
            response.sendRedirect(request.getContextPath() + "/movie?action=admin");
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    private void deleteMovie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get movie ID from the form
        int movieId = Integer.parseInt(request.getParameter("movieId"));
        System.out.println("delete movie id is "+movieId);
        // Delete the movie from the database
        boolean success = movieDAO.deleteMovie(movieId);

        // Redirect to appropriate page based on the result
        if (success) {
            response.sendRedirect(request.getContextPath() + "/movie?action=admin");
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            // Display all movies
            List<Movie> movies = movieDAO.getAllMovies();
            request.setAttribute("movies", movies);
            List<Feedback> feedbacks = feedBackDao.getAllFeedback();
            List<Package> packages = packageDao.getAllPackages();
            request.setAttribute("movies", movies);
            request.setAttribute("feedbacks", feedbacks);
            request.setAttribute("packages", packages);
            request.getRequestDispatcher("/movie_home.jsp").forward(request, response);
        } 
        else if(action.equals("admin")) {
        	List<Movie> movies = movieDAO.getAllMovies();
            request.setAttribute("movies", movies);
            request.getRequestDispatcher("/admin_movies.jsp").forward(request, response);
        }
        else if (action.equals("edit")) {
            // Display the form to edit a movie
            int movieId = Integer.parseInt(request.getParameter("movieId"));
            Movie movie = movieDAO.getMovieById(movieId);
            String jsonMovie = "{";
            jsonMovie += "\"movieId\":" + movie.getMovieId() + ",";
            jsonMovie += "\"name\":\"" + movie.getName() + "\",";
            jsonMovie += "\"releasedYear\":" + movie.getReleasedYear() + ",";
            jsonMovie += "\"description\":\"" + movie.getDescription() + "\",";
            jsonMovie += "\"imageUrl\":\"" + movie.getImageUrl() + "\",";
            jsonMovie += "\"type\":\"" + movie.getType() + "\"";
            jsonMovie += "}";

            // Set the JSON response
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonMovie);
        }
        else if(action.equals("delete")) {
     
            deleteMovie(request, response);
    
        }
        else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
}
