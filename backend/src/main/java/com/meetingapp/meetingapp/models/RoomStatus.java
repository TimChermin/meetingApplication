package com.meetingapp.meetingapp.models;

public class RoomStatus {
    private RoomStatusEnum status;
    private String subject;
    private long startTime;
    private long endTime;
    private Room room;
    private String name;
    private String location;

    public RoomStatus(String subject, long startTime, long endTime, String name, String location, RoomStatusEnum status) {
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.location = location;
        this.status = status;
    }

    public RoomStatus(String name, String location, RoomStatusEnum status) {
        this.name = name;
        this.location = location;
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public RoomStatusEnum getStatus() {
        return status;
    }

    public void setStatus(RoomStatusEnum status) {
        this.status = status;
    }
}
