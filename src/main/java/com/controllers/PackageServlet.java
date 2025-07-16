package com.controllers;

import com.dao.PackageDAO;
import com.model.Package;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PackageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private PackageDAO packageDAO = new PackageDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "add":
                    addPackage(request, response);
                    break;
                case "update":
                    updatePackage(request, response);
                    break;
                case "delete":
                    deletePackage(request, response);
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/error.jsp");
            }
        }
    }

    private void addPackage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get package data from the form
        String packageName = request.getParameter("packageName");
        double price = Double.parseDouble(request.getParameter("price"));
        String description = request.getParameter("description");

        // Create a new package
        Package packageObj = new Package();
        packageObj.setPackageName(packageName);
        packageObj.setPrice(price);
        packageObj.setDescription(description);

        // Add the package to the database
        boolean success = packageDAO.addPackage( packageName,  price,  description);

        // Redirect to appropriate page based on the result
        if (success) {
            response.sendRedirect(request.getContextPath() + "/package?action=admin");
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    private void updatePackage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get package data from the form
        int packageId = Integer.parseInt(request.getParameter("packageId"));
        String packageName = request.getParameter("packageName");
        double price = Double.parseDouble(request.getParameter("price"));
        String description = request.getParameter("description");

        // Create a package object with updated data
        Package updatedPackage = new Package();
        updatedPackage.setPackageId(packageId);
        updatedPackage.setPackageName(packageName);
        updatedPackage.setPrice(price);
        updatedPackage.setDescription(description);

        // Update the package in the database
        boolean success = packageDAO.updatePackage(updatedPackage);

        // Redirect to appropriate page based on the result
        if (success) {
            response.sendRedirect(request.getContextPath() + "/package?action=admin");
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    private void deletePackage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get package ID from the form
        int packageId = Integer.parseInt(request.getParameter("packageId"));

        // Delete the package from the database
        boolean success = packageDAO.deletePackage(packageId);

        // Redirect to appropriate page based on the result
        if (success) {
            response.sendRedirect(request.getContextPath() + "/package?action=admin");
        } else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            // Display all packages
            List<Package> packages = packageDAO.getAllPackages();
            request.setAttribute("packages", packages);
            request.getRequestDispatcher("/packageList.jsp").forward(request, response);
        } 
        else if(action.equals("admin")) {
        	 List<Package> packages = packageDAO.getAllPackages();
             request.setAttribute("packages", packages);
             request.getRequestDispatcher("/admin_packages.jsp").forward(request, response);
        }
        else if (action.equals("edit")) {
            // Display the form to edit a package
            int packageId = Integer.parseInt(request.getParameter("packageId"));
            Package packageObj = packageDAO.getPackageById(packageId);
            // Convert packageObj to JSON format
            String jsonResponse = "{";
            jsonResponse += "\"packageId\":" + packageObj.getPackageId() + ",";
            jsonResponse += "\"name\":\"" + packageObj.getPackageName() + "\",";
            jsonResponse += "\"price\":" + packageObj.getPrice() + ",";
            // Add other package attributes

            // Remove the trailing comma and close the JSON object
            jsonResponse = jsonResponse.substring(0, jsonResponse.length() - 1);
            jsonResponse += "}";

            // Set the content type of the response to be JSON
            response.setContentType("application/json");

            // Write the JSON response to the output stream
            response.getWriter().write(jsonResponse);
        }
        
        else if(action.equals("delete")) {
        	  deletePackage(request, response);
        }
        else {
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
}
