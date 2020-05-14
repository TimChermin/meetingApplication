package com.meetingapp.meetingapp.models;

public class RoomAppointment {
    private String id;
    private String subject;
    private long startTime;
    private long endTime;
    private String location;

    public RoomAppointment(String id, String subject, long startTime, long endTime, String location) {
        this.id = id;
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }

    public String getId() {return id;}

    public String getSubject() {
        return subject;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public String getLocation() {
        return location;
    }

}
