package com.example.costall.models;

public class Participants {
    private String id;
    private String name;
    private String surname;
    private String role;
    private String salary;
    private String email;
    private String meetingCost;

    public Participants(String id, String name, String surname, String role, String salary, String email, String meetingCost) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.salary = salary;
        this.email = email;
        this.meetingCost = meetingCost;
    }

    public Participants (String id, String name, String surname, String role) {
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getRole() {
        return role;
    }

    public String getSalary() {
        return salary;
    }

    public String getEmail() {
        return email;
    }

    public double getCostPerHour(){
        return Math.round( Double.parseDouble(this.salary) /(22*8))/100.0; // YOU WILL NEED TO CHANGE THIS TO HOW YOU CALCULATE THE HOURLY RATE
    }

    public double getCostPerMinute(){
        return Math.round(Double.parseDouble(this.salary)/(22*8*60))/100.0; // YOU WILL NEED TO CHANGE THIS AS WELL.
    }

    public double getCostperSecond() {
        return Math.round(Double.parseDouble(this.salary)/(22*8*60*60));
    }

    public String getId() {
        return id;
    }

    public String getMeetingCost() {
        return meetingCost;
    }

    public void setMeetingCost(String meetingCost) {
        this.meetingCost = meetingCost;
    }


    public String toString () {
        return name + " " + surname;
    }

}
