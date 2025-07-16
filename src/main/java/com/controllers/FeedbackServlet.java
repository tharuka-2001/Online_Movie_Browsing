package com.controllers;

import com.dao.FeedbackDAO;
import com.model.Feedback;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FeedbackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FeedbackDAO feedbackDAO = new FeedbackDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    addFeedback(request, response);
                    break;
                case "update":
                    updateFeedback(request, response);
                    break;
                case "delete":
                    deleteFeedback(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/error.jsp");
            }
        }
    }

    private void addFeedback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userName = request.getParameter("userName");
        String comment = request.getParameter("comment");
        int rate = Integer.parseInt(request.getParameter("rate"));
        int id = Integer.parseInt(request.getParameter("movieId"));
        Feedback feedback = new Feedback();
        feedback.setUserId(1);
        feedback.setUserName(userName);
        feedback.setComment(comment);
        feedback.setRate(rate);
        feedback.setMovieId(id);
        boolean success = feedbackDAO.addFeedback(1, userName, comment, rate,id);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/feedback");
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    private void updateFeedback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int feedbackId = Integer.parseInt(request.getParameter("feedbackId"));
        int userId = Integer.parseInt(request.getParameter("movieId"));
        String userName = request.getParameter("userName");
        String comment = request.getParameter("comment");
        int rate = Integer.parseInt(request.getParameter("rate"));
  
        Feedback updatedFeedback = new Feedback();
        updatedFeedback.setFeedbackId(feedbackId);
        updatedFeedback.setMovieId(userId);
        updatedFeedback.setUserName(userName);
        updatedFeedback.setComment(comment);
        updatedFeedback.setRate(rate);
        updatedFeedback.setUserId(1);
        boolean success = feedbackDAO.updateFeedback(updatedFeedback);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/feedback");
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    private void deleteFeedback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int feedbackId = Integer.parseInt(request.getParameter("feedbackId"));

        boolean success = feedbackDAO.deleteFeedback(feedbackId);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/feedback");
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            List<Feedback> feedbackList = feedbackDAO.getAllFeedback();
            request.setAttribute("feedbackList", feedbackList);
            request.getRequestDispatcher("/feedbackList.jsp").forward(request, response);
        }
        
        else if(action.equals("delete")) {
deleteFeedback(request,response);
        }
        else if (action.equals("edit")) {
            int feedbackId = Integer.parseInt(request.getParameter("feedbackId"));
            Feedback feedback = feedbackDAO.getFeedbackById(feedbackId);

            String jsonResponse = "{";
            jsonResponse += "\"feedbackId\":" + feedback.getFeedbackId() + ",";
            jsonResponse += "\"userId\":" + feedback.getUserId() + ",";
            jsonResponse += "\"userName\":\"" + feedback.getUserName() + "\",";
            jsonResponse += "\"comment\":\"" + feedback.getComment() + "\",";
            jsonResponse += "\"rate\":" + feedback.getRate();
            jsonResponse += "\"movieId\":" + feedback.getMovieId();
            // Add other feedback attributes

            // Remove the trailing comma and close the JSON object
            jsonResponse = jsonResponse.substring(0, jsonResponse.length() - 1);
            jsonResponse += "}";

            // Set the content type of the response to be JSON
            response.setContentType("application/json");

            // Write the JSON response to the output stream
            response.getWriter().write(jsonResponse);
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
}
