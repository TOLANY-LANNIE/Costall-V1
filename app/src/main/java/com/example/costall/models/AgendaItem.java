package com.example.costall.models;

public class AgendaItem {
    private String agendaID,agendaDescription, participantID,allocatedTime, discussion,conclusion, name,surname;
    public AgendaItem(){}

    public AgendaItem(String agendaID, String agendaDescription, String participantID, String allocatedTime, String discussion, String conclusion, String name, String surname) {
        this.agendaID = agendaID;
        this.agendaDescription = agendaDescription;
        this.participantID = participantID;
        this.allocatedTime = allocatedTime;
        this.discussion = discussion;
        this.conclusion = conclusion;
        this.name = name;
        this.surname = surname;
    }

    public String getAgendaID() {
        return agendaID;
    }

    public void setAgendaID(String agendaID) {
        this.agendaID = agendaID;
    }

    public String getAgendaDescription() {
        return agendaDescription;
    }

    public void setAgendaDescription(String agendaDescription) {
        this.agendaDescription = agendaDescription;
    }

    public String getParticipantID() {
        return participantID;
    }

    public void setParticipantID(String participantID) {
        this.participantID = participantID;
    }

    public String getAllocatedTime() {
        return allocatedTime;
    }

    public void setAllocatedTime(String allocatedTime) {
        this.allocatedTime = allocatedTime;
    }

    public String getDiscussion() {
        return discussion;
    }

    public void setDiscussion(String discussion) {
        this.discussion = discussion;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return  "agendaID='" + agendaID + '\'' +
                ", agendaDescription='" + agendaDescription + '\'' +
                ", participantID='" + participantID + '\'' +
                ", allocatedTime='" + allocatedTime + '\'' +
                ", discussion='" + discussion + '\'' +
                ", conclusion='" + conclusion + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'';
    }
}
