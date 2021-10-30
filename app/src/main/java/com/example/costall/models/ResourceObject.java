package com.example.costall.models;

public class ResourceObject {
    private String session,resourceName, purpose, cost;

    public ResourceObject(){}

    public ResourceObject(String session, String resourceName, String purpose, String cost) {
        this.session = session;
        this.resourceName = resourceName;
        this.purpose = purpose;
        this.cost = cost;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
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

    @Override
    public String toString() {
        return "ResourceObject{" +
                "session='" + session + '\'' +
                ", resourceName='" + resourceName + '\'' +
                ", purpose='" + purpose + '\'' +
                ", cost='" + cost + '\'' +
                '}';
    }
}
