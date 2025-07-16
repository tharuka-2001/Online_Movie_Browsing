<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.model.User" %>
<% User user = (User) request.getAttribute("user");%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit User Profile</title>
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

        .form-container {
            max-width: 400px;
            margin: auto;
            margin-top: 50px;
            padding: 20px;
            background-color: #333; /* Dark card background color */
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            font-size: 16px;
            margin-bottom: 5px;
        }

        .form-group input {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        .form-group button {
            background-color: #007bff; /* Blue button color */
            color: #fff; /* White text color */
            padding: 10px 15px;
            font-size: 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .form-group button:hover {
            background-color: #0056b3; /* Darker blue on hover */
        }

        .delete-profile {
            margin-top: 20px;
            text-align: center;
        }

        .delete-profile button {
            background-color: #dc3545; /* Red button color */
            color: #fff; /* White text color */
            padding: 10px 15px;
            font-size: 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .delete-profile button:hover {
            background-color: #c82333; /* Darker red on hover */
        }
    </style>
</head>
<body>
    <div class="navbar">
        <a href="#" class="navbar-title">Edit User Profile</a>
        <img src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png" alt="User Avatar" class="avatar" onclick="location.href='userProfile.jsp';">
    </div>
	 <a href="feedback"> <button class="btn" >My Feedbacks</button></a>
    <div class="form-container">
        <% if(user!=null){%>
        <form action="<%= request.getContextPath() %>/user?action=update" method="post">
        <div class="form-group">
                <label for="editUserName">ID:</label>
                <input type="text" id="editUserName" name="userId" value="<%= user.getUserId() %>" readOnly required>
            </div>
            <div class="form-group">
                <label for="editUserName">Username:</label>
                <input type="text" id="editUserName" name="userName" value="<%= user.getUserName() %>" required>
            </div>
            <div class="form-group">
                <label for="editEmail">Email:</label>
                <input type="email" id="editEmail" name="email" value="<%= user.getEmail() %>" required>
            </div>
           
            <div class="form-group">
                <label for="editContactNumber">Contact Number:</label>
                <input type="tel" id="editContactNumber" name="contactNumber" value="<%= user.getContactNumber() %>" required>
            </div>
            <div class="form-group">
                <button type="submit">Update Profile</button>
            </div>
        </form>

        <div class="delete-profile">
            <button onclick="confirmDelete()">Delete Profile</button>
        </div>
        <%} %>
    </div>

    <script>
        function confirmDelete() {
            var confirmDelete = confirm("Are you sure you want to delete your profile?");
            if (confirmDelete) {
                window.location.href = "<%= request.getContextPath() %>/user?action=delete&userId=<%= user.getUserId() %>";
            }
        }
    </script>
</body>
</html>
