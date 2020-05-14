package com.meetingapp.meetingapp.models;

public class Room {
    private String name;
    private String location;
    private String emailAddress;

    public Room(String name, String location, String emailAddress) {
        this.name = name;
        this.location = location;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
