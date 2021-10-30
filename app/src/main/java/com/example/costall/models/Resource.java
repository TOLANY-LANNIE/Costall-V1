package com.example.costall.models;

public class Resource {
    private String resourceName, purpose, cost;

    public Resource(){}

    public Resource(String resourceName, String purpose, String cost) {
        this.resourceName = resourceName;
        this.purpose = purpose;
        this.cost = cost;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
