package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.controllers.ConnectionManager;
import com.model.Package;

public class PackageDAO {
    private Connection connection;

    public PackageDAO() {
        this.connection = ConnectionManager.getConnection();
    }

    public boolean addPackage(String packageName, double price, String description) {
        try {
            String sql = "INSERT INTO packages (package_name, price, description) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, packageName);
                statement.setDouble(2, price);
                statement.setString(3, description);

                int rowsInserted = statement.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Package> getAllPackages() {
        List<Package> packages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM packages";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int packageId = resultSet.getInt("package_id");
                    String packageName = resultSet.getString("package_name");
                    double price = resultSet.getDouble("price");
                    String description = resultSet.getString("description");

                    Package packageObj = new Package();
                    packageObj.setPackageId(packageId);
                    packageObj.setPackageName(packageName);
                    packageObj.setPrice(price);
                    packageObj.setDescription(description);
                    packages.add(packageObj);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packages;
    }

    public boolean updatePackage(Package packageObj) {
        try {
            String sql = "UPDATE packages SET package_name = ?, price = ?, description = ? WHERE package_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, packageObj.getPackageName());
                statement.setDouble(2, packageObj.getPrice());
                statement.setString(3, packageObj.getDescription());
                statement.setInt(4, packageObj.getPackageId());

                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePackage(int packageId) {
        try {
            String sql = "DELETE FROM packages WHERE package_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, packageId);

                int rowsDeleted = statement.executeUpdate();
                return rowsDeleted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Package getPackageById(int packageId) {
        try {
            String sql = "SELECT * FROM packages WHERE package_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, packageId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String packageName = resultSet.getString("package_name");
                        double price = resultSet.getDouble("price");
                        String description = resultSet.getString("description");

                        Package packageObj = new Package();
                        packageObj.setPackageId(packageId);
                        packageObj.setPackageName(packageName);
                        packageObj.setPrice(price);
                        packageObj.setDescription(description);
                        return packageObj;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
