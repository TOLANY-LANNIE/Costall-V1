package com.example.costall.models;

import java.io.Serializable;

public class Meeting implements Serializable {
    private String meetingTitle, meetingType, meetingDate, meetingStart, meetingEnd;

    public Meeting(String meetingTitle, String meetingType, String meetingDate, String meetingStart, String meetingEnd) {
        this.meetingTitle = meetingTitle;
        this.meetingType = meetingType;
        this.meetingDate = meetingDate;
        this.meetingStart = meetingStart;
        this.meetingEnd = meetingEnd;
    }

    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public String getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(String meetingType) {
        this.meetingType = meetingType;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getMeetingStart() {
        return meetingStart;
    }

    public void setMeetingStart(String meetingStart) {
        this.meetingStart = meetingStart;
    }

    public String getMeetingEnd() {
        return meetingEnd;
    }

    public void setMeetingEnd(String meetingEnd) {
        this.meetingEnd = meetingEnd;
    }
}
