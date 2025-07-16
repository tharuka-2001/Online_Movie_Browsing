package com.controllers;

import com.dao.UserDAO;
import com.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    addUser(request, response);
                    break;
                case "update":
                    updateUser(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                case "login":
                    loginUser(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/error.jsp");
            }
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get user data from the form
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String contactNumber = request.getParameter("contactNumber");

        // Create a new user
        User user = new User();
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(password);
        user.setContactNumber(contactNumber);

        // Add the user to the database
        boolean success = userDAO.addUser( userName,  email,  password,  contactNumber);

        // Redirect to appropriate page based on the result
        if (success) {
        	Cookie emailCookie = new Cookie("userEmail", email);
            emailCookie.setMaxAge(24 * 60 * 60); // Cookie will last for 24 hours (adjust as needed)
            response.addCookie(emailCookie);
            response.sendRedirect(request.getContextPath() + "/movie");
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get user data from the form
        int userId = Integer.parseInt(request.getParameter("userId"));
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
       
        String contactNumber = request.getParameter("contactNumber");

        // Create a user object with updated data
        User updatedUser = new User(userId, userName, email, "as", contactNumber);

        // Update the user in the database
        boolean success = userDAO.updateUser(updatedUser);

        // Redirect to appropriate page based on the result
        if (success) {
        	Cookie emailCookie = new Cookie("userEmail", email);
            emailCookie.setMaxAge(24 * 60 * 60); // Cookie will last for 24 hours (adjust as needed)
            response.addCookie(emailCookie);
            response.sendRedirect(request.getContextPath() + "/movie");
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get user ID from the form
        int userId = Integer.parseInt(request.getParameter("userId"));

        // Delete the user from the database
        boolean success = userDAO.deleteUser(userId);

        // Redirect to appropriate page based on the result
        if (success) {
            response.sendRedirect(request.getContextPath() + "/login_user.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    private void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get login credentials from the form
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Perform login authentication
        boolean isAuthenticated = userDAO.loginUser(email, password);

        // Redirect to appropriate page based on the authentication result
        if (isAuthenticated) {
        	  Cookie emailCookie = new Cookie("userEmail", email);
              emailCookie.setMaxAge(24 * 60 * 60); // Cookie will last for 24 hours (adjust as needed)
              response.addCookie(emailCookie);
            response.sendRedirect(request.getContextPath() + "/movie");
        } else {
            response.sendRedirect(request.getContextPath() + "/login_user.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Cookie[] cookies = request.getCookies();
        String userEmail = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userEmail")) {
                    userEmail = cookie.getValue();
                    break;
                }
            }
        }

        if (action == null) {
            // Display all users
            List<User> users = userDAO.getAllUsers();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/userList.jsp").forward(request, response);
        } else if (action.equals("edit")) {
            // Display the form to edit a user
           
            User user = userDAO.getUserByEmail(userEmail);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/editUser.jsp").forward(request, response);
        }else if(action.equals("delete")) {
        	deleteUser(request,response);
        }
        
        else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
}
