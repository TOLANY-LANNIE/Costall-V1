package com.example.costall.models;

public class MeetingSession {
    private String meetingTitle;
    private String meetingGoal;
    private String organizerEmail;
    private String date;
    private String startTime;;
    private String endTime;
    private String meetingType;

    public MeetingSession() {
    }

    public MeetingSession(String meetingTitle, String meetingGoal, String organizerEmail,
                          String date, String startTime, String endTime, String meetingType) {
        this.meetingTitle = meetingTitle;
        this.meetingGoal = meetingGoal;
        this.organizerEmail = organizerEmail;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.meetingType = meetingType;
    }

    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public String getMeetingGoal() {
        return meetingGoal;
    }

    public void setMeetingGoal(String meetingGoal) {
        this.meetingGoal = meetingGoal;
    }

    public String getOrganizerEmail() {
        return organizerEmail;
    }

    public void setOrganizerEmail(String organizerEmail) {
        this.organizerEmail = organizerEmail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(String meetingType) {
        this.meetingType = meetingType;
    }
}
