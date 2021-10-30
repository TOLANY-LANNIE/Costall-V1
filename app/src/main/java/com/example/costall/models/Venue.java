package com.example.costall.models;

public class Venue {
    private String venue,location,cost;

    public Venue(){}

    public Venue(String venue, String location, String cost) {
        this.venue = venue;
        this.location = location;
        this.cost = cost;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
