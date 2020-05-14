package com.meetingapp.meetingapp.models;

import java.util.Date;

public class ExtendAppointmentTime {
    private String roomName;
    private int extendedTime;

    public ExtendAppointmentTime( int extendedTime,String roomName) {
        this.roomName = roomName;
        this.extendedTime = extendedTime;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getExtendedTime() {
        return extendedTime;
    }

    public void setExtendedTime(int extendedTime) {
        this.extendedTime = extendedTime;
    }
}
