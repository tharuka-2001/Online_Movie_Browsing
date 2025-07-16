<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.model.Package" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Package Management</title>
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
    <h2>Package Management</h2>

    <!-- Display success or error messages -->

    <!-- Display all packages -->
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Package ID</th>
                <th>Name</th>
                <th>Price</th>
                <th>Description</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
            List<Package> packages = (List<Package>) request.getAttribute("packages");
            if (packages != null) {
                for (Package packageObj : packages) { %>
                    <tr>
                        <td><%= packageObj.getPackageId() %></td>
                        <td><%= packageObj.getPackageName() %></td>
                        <td><%= packageObj.getPrice() %></td>
                        <td><%= packageObj.getDescription() %></td>
                        <td>
                            <!-- Edit button with data-package-id attribute -->
                            <a href="#" class="btn btn-primary editPackageLink" data-package-id="<%= packageObj.getPackageId() %>">Edit</a>
                            <!-- Delete button with data-package-id attribute -->
                            <a href="#" class="btn btn-danger deletePackageLink" data-package-id="<%= packageObj.getPackageId() %>">Delete</a>
                        </td>
                    </tr>
                <%} } %>
        </tbody>
    </table>
    <!-- Add a button to create a new package -->
    <button class="btn btn-success" id="addPackageBtn">Add Package</button>

</div>

<!-- Add Package Modal -->
<div class="modal fade" id="addPackageModal" tabindex="-1" role="dialog" aria-labelledby="addPackageModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addPackageModalLabel">Add Package</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <!-- Add Package Form with client-side validation -->
                <form action="<%= request.getContextPath() %>/package?action=add" method="post" id="addPackageForm">
                    <div class="form-group">
                        <label for="addPackageName">Name:</label>
                        <input type="text" class="form-control" id="addPackageName" name="packageName" required>
                    </div>
                    <div class="form-group">
                        <label for="addPrice">Price:</label>
                        <input type="number" class="form-control" id="addPrice" name="price" required>
                    </div>
                    <div class="form-group">
                        <label for="addDescription">Description:</label>
                        <textarea class="form-control" id="addDescription" name="description" required></textarea>
                    </div>
                    <!-- Add other form fields as needed -->
                    <button type="submit" class="btn btn-primary">Add Package</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Edit Package Modal -->
<div class="modal fade" id="editPackageModal" tabindex="-1" role="dialog" aria-labelledby="editPackageModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editPackageModalLabel">Edit Package</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <!-- Edit Package Form with client-side validation -->
                <form action="<%= request.getContextPath() %>/package?action=update" method="post" id="editPackageForm">
                    <input type="text" class="form-control" id="editPackageId" style="display:none" name="packageId" required>
                    <div class="form-group">
                        <label for="editPackageName">Name:</label>
                        <input type="text" class="form-control" id="editPackageName" name="packageName" required>
                    </div>
                    <div class="form-group">
                        <label for="editPrice">Price:</label>
                        <input type="number" class="form-control" id="editPrice" name="price" required>
                    </div>
                    <div class="form-group">
                        <label for="editDescription">Description:</label>
                        <textarea class="form-control" id="editDescription" name="description" required></textarea>
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
                Are you sure you want to delete this package?
            </div>
            <div class="modal-footer">
                <a href="#" id="deletePackageLink" class="btn btn-danger">Delete</a>
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
        // Show add package modal on button click
        $('#addPackageBtn').click(function() {
            $('#addPackageModal').modal('show');
        });

        // Show edit package modal on edit button click
        $('.editPackageLink').click(function() {
            var packageId = $(this).data('package-id');
            // Load package data using AJAX and populate the edit modal
            loadEditData(packageId);
        });

        // Show delete confirmation modal on delete button click
        $('.deletePackageLink').click(function() {
            var packageId = $(this).data('package-id');
            // Set the delete link href dynamically based on packageId
            $('#deletePackageLink').attr('href', '<%= request.getContextPath() %>/package?action=delete&packageId=' + packageId);
            $('#deleteConfirmationModal').modal('show');
        });

        // Client-side validation for Add Package Form
        $('#addPackageForm').submit(function(event) {
            var isValid = true;
            // Example: Add validation logic for each field
            if ($('#addPackageName').val().trim() === '') {
                alert('Package Name is required.');
                isValid = false;
            }
            // Add other validation rules as needed

            if (!isValid) {
                event.preventDefault(); // Prevent form submission
            }
        });

        // Client-side validation for Edit Package Form
        $('#editPackageForm').submit(function(event) {
            var isValid = true;
            // Example: Add validation logic for each field
            if ($('#editPackageName').val().trim() === '') {
                alert('Package Name is required.');
                isValid = false;
            }
            // Add other validation rules as needed

            if (!isValid) {
                event.preventDefault(); // Prevent form submission
            }
        });

        function loadEditData(packageId) {
            // Make an AJAX request to fetch data for the selected package
            fetch('<%= request.getContextPath() %>/package?action=edit&packageId=' + packageId, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            })
            .then(response => response.json())
            .then(data => {
                $('#editPackageId').val(data.packageId);
                $('#editPackageName').val(data.name);
                $('#editPrice').val(data.price);
                $('#editDescription').val(data.description);
                // Show the edit modal
                $('#editPackageModal').modal('show');
            })
            .catch(error => {
                // Handle errors
                console.error('Failed to fetch data for package ID: ' + packageId);
            });
        }
    });
</script>

</body>
</html>
