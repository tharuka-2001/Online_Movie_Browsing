<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background-image: url('https://image.cnbcfm.com/api/v1/image/104768589-movies-anywhere.JPG?v=1507816437&w=1920&h=1080'); /* Add the path to your background image */
            background-size: cover;
            background-position: center;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        .login-container {
            background-color: rgba(0, 0, 0, 0.8);
            padding: 20px;
            border-radius: 8px;
            text-align: center;
        }

        input {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 2px solid #ffd700; /* Yellow border */
            border-radius: 4px;
            box-sizing: border-box;
        }

        button {
            background-color: #ffd700; /* Dark yellow background */
            color: #000;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #ffcc00; /* Lighter yellow on hover */
        }

        a {
            color: #ffd700; /* Yellow text */
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2 style="color: #ffd700;">Login</h2>
        <form action="user?action=login" method="post">
            <input type="text" id="username" name="email" placeholder="Username" required>
            <br>
            <input type="password" id="password" name="password" placeholder="Password" required>
            <br>
            <button type="submit">Login</button>
        </form>
        <p style="color: #fff;">Don't have an account? <a href="signup.jsp">Sign up</a></p>
    </div>

    <script>
        function validateForm() {
            var username = document.getElementById("username").value;
            var password = document.getElementById("password").value;

            if (username === "" || password === "") {
                alert("Username and password are required");
                return false;
            }

            // Add more complex validation logic as needed

            return true;
        }
    </script>
</body>
</html>
