package com.meetingapp.meetingapp.models;

import java.util.Date;

public class UpdateAppointmentTime {
    private String roomName;
    private boolean isStartDate;
    private Date updateTime;

    public UpdateAppointmentTime(String roomName) {
        this.roomName = roomName;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public boolean isStartDate() {
        return isStartDate;
    }

    public void setIsStartDate(boolean startDate) {
        isStartDate = startDate;
    }
}
