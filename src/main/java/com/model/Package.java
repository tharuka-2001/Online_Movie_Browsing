package com.model;

public class Package {
    private int packageId;
    private String packageName;
    private double price;
    private String description;

    // Constructors

    public Package() {
    }

    public Package(int packageId, String packageName, double price, String description) {
        this.packageId = packageId;
        this.packageName = packageName;
        this.price = price;
        this.description = description;
    }

    // Getters and Setters

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
